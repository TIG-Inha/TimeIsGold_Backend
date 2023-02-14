package TimeIsGold.TimeIsGold.api.group;

import TimeIsGold.TimeIsGold.api.ApiResponse;
import TimeIsGold.TimeIsGold.api.group.dto.GroupStartResponseDto;
import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.group.GroupRepository;
import TimeIsGold.TimeIsGold.domain.group.Position;
//import TimeIsGold.TimeIsGold.domain.group.Team;
//import TimeIsGold.TimeIsGold.domain.group.TeamRepository;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMember;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMemberRepository;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.otp.Otp;
import TimeIsGold.TimeIsGold.exception.group.SessionExpireException;
import TimeIsGold.TimeIsGold.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class GroupController {

    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    public GroupController(GroupService groupService, GroupRepository groupRepository, GroupMemberRepository groupMemberRepository) {
        this.groupService = groupService;
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    //SSE 연결
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/group/{groupName}", produces = "text/event-stream")
    public ApiResponse start(@PathVariable("groupName") String groupName,
                             @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                             HttpServletRequest request) {


        //사용자 정보 이름 불러오기
        HttpSession session=request.getSession();
        if(session==null){
            throw new SessionExpireException();
        }
        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);

        //참여자 수 데이터에 저장
        Group group=Group.create(groupName, 1L);
        GroupMember groupMember = GroupMember.create(loginMember, group, "HOST");
        String otp=group.getGroupOtp().getOtpCode();
        groupRepository.save(group);
        groupMemberRepository.save(groupMember);


        //SSE 연결
        SseEmitter emitter=groupService.start(group.getId(), loginMember.getId(), lastEventId);


        //ResponseDto
        GroupStartResponseDto response = new GroupStartResponseDto(otp, emitter);
        return ApiResponse.createSuccess(response);

    }

}
