package TimeIsGold.TimeIsGold.api.group;

import TimeIsGold.TimeIsGold.api.ApiResponse;
import TimeIsGold.TimeIsGold.api.group.dto.GroupOtpResponseDto;
import TimeIsGold.TimeIsGold.api.group.dto.GroupStartResponseDto;
import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.group.GroupRepository;
//import TimeIsGold.TimeIsGold.domain.group.Team;
//import TimeIsGold.TimeIsGold.domain.group.TeamRepository;
import TimeIsGold.TimeIsGold.domain.group.Position;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMember;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMemberRepository;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.member.MemberRepository;
import TimeIsGold.TimeIsGold.exception.group.SessionExpireException;
import TimeIsGold.TimeIsGold.exception.group.GroupException;
import TimeIsGold.TimeIsGold.exception.login.LoginException;
import TimeIsGold.TimeIsGold.service.group.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RestController
public class GroupController {

    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    private final MemberRepository memberRepository;

    public GroupController(GroupService groupService, GroupRepository groupRepository, GroupMemberRepository groupMemberRepository, MemberRepository memberRepository) {
        this.groupService = groupService;
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.memberRepository = memberRepository;
    }

    //SSE 연결
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/group/{groupName}", produces = "text/event-stream")
    public SseEmitter start(@PathVariable("groupName") String groupName,
                             @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                             HttpServletRequest request) {


        //사용자 정보 이름 불러오기
        HttpSession session=request.getSession(false);

        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);
        if(loginMember==null){
            throw new SessionExpireException("세션이 만료되었습니다.");
        }

        //참여자 수 데이터에 저장
        Group group=Group.create(groupName, 1L);

        //group id를 생성자 session에 저장
        session.setAttribute(SessionConstants.GROUP, group);
        session.setMaxInactiveInterval(600);

        groupRepository.save(group);

        //생성자 객체 찾아 그룹 멤버에 HOST 넣기
        Member member=memberRepository.findByUserIdAndPw(loginMember.getUserId(), loginMember.getPw());

        if(member==null){
            throw new LoginException("로그인 오류");
        }

        GroupMember groupMember = GroupMember.create(member, group, Position.HOST);
        groupMemberRepository.save(groupMember);


        //SSE 연결
        SseEmitter emitter=groupService.subscribe(group.getId(), loginMember.getId(), lastEventId, 1L);


        //ResponseDto
        //GroupStartResponseDto response = new GroupStartResponseDto(otp, emitter);
        return emitter;

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/group/otp")
    public ApiResponse otp(HttpServletRequest request){
        HttpSession session=request.getSession(false);


        if(session==null){
            throw new SessionExpireException("세션이 만료되었습니다.");
        }

        Group temp = (Group) session.getAttribute(SessionConstants.GROUP);

        if(temp==null){
            throw new SessionExpireException("그룹 생성 유효 시간이 만료되었습니다.");
        }

        Group group = groupRepository.findByIdAndName(temp.getId(),temp.getName());

        if(group==null){
            throw new GroupException("그룹이 유효하지 않습니다.");
        }

        String otp=group.changeOtp().getOtpCode();
        GroupOtpResponseDto response = new GroupOtpResponseDto(otp);

        return ApiResponse.createSuccess(response);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/group/participate/{otp}", produces = "text/event-stream")
    public SseEmitter participate(@PathVariable("otp") String otp,
                                  @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                  HttpServletRequest request){
        //사용자 정보 이름 불러오기
        HttpSession session=request.getSession(false);

        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);
        if(loginMember==null){
            throw new SessionExpireException("세션이 만료되었습니다.");
        }

        //그룹 정보 불러오기
        Group group=groupRepository.findByOtp(otp);

        if(group==null){
            throw new GroupException("그룹이 유효하지 않습니다.");
        }

        session.setAttribute(SessionConstants.GROUP, group);

        //SSE 연결
        SseEmitter emitter=groupService.participate(loginMember.getId(), loginMember.getUserId(), loginMember.getPw(),otp, lastEventId, group);

        return emitter;
    }

    // Group 취소, 나가기 api
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/group/cancel")
    public ApiResponse cancel(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session==null){
            throw new SessionExpireException("세션 만료");
        }

        Group group = (Group) session.getAttribute(SessionConstants.GROUP);

        groupService.cancel(group.getId(),group.getName());

        return ApiResponse.createSuccess(null);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/group/out")
    public void out(){

    }
}
