package TimeIsGold.TimeIsGold.service.group;

import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.domain.group.EmitterRepository;
import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.group.GroupRepository;
import TimeIsGold.TimeIsGold.domain.group.Position;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMember;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMemberRepository;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.member.MemberRepository;
import TimeIsGold.TimeIsGold.domain.schedule.DayOfWeek;
import TimeIsGold.TimeIsGold.domain.schedule.Schedule;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableForm;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableRepository;
import TimeIsGold.TimeIsGold.exception.group.GroupException;
import TimeIsGold.TimeIsGold.exception.group.SessionExpireException;
import TimeIsGold.TimeIsGold.exception.login.LoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
//import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    @Autowired
    private final EmitterRepository emitterRepository;
    @Autowired
    private final GroupRepository groupRepository;
    @Autowired
    private final GroupMemberRepository groupMemberRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final TimetableRepository timetableRepository;

    private String includeTime(Long groupId, Long userId){
        String id=groupId+"_"+userId+"_"+System.currentTimeMillis();

        return id;
    }

    public SseEmitter subscribe(Long groupId, Long userId, String lastEventId, Long num){
        //데이터가 유실된 시점을 파악하기 위해
        //emitter 구분을 위해
        String emitterId=includeTime(groupId, userId);

        //유효 시간 주기
        SseEmitter sseEmitter=new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(emitterId ,sseEmitter);

        //SseEmitter의 시간 초과 및 네트워크 오류를 포함한 모든 이유로 비동기 요청이 정상 동작할 수 없다면 저장해둔 SseEmitter를 삭제한다.
        sseEmitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        sseEmitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        //ConcurrentHashMap이기 때문에 보낸 순서대로 저장되지 않으므로, 현재 시간을 포함시킨다.
        String eventId=includeTime(groupId, userId);
        //SseEmiiter가 생성되면 더미 데이터를 보내야 함, 하나의 데이터도 전송되지 않는다면 유효 시간이 끝날 때 503 응답 발생
        sendToClient(sseEmitter, emitterId, eventId, num);
        sendToAllGroupMember(groupId, num);

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (!lastEventId.isEmpty()) {
            sendLostData(lastEventId, groupId, userId, eventId, sseEmitter);
        }
        return sseEmitter;
    }

    private void sendLostData(String lastEventId, Long groupId, Long userId, String emitterId, SseEmitter sseEmitter){
        Map<String, Long> events = emitterRepository.findAllEventCacheStartWithById(groupId + "_" + userId);
        events.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendToClient(sseEmitter, emitterId, entry.getKey(), entry.getValue()));
    }

    private void sendToClient(SseEmitter emitter, String emitterId, String eventId, Long data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
            throw new RuntimeException("연결 오류!");
        }
    }

    private void sendToAllGroupMember(Long groupId, Long num){
        Map<String, SseEmitter> m = emitterRepository.findAllEmitterStartWithById(String.valueOf(groupId));

        m.forEach((key, value)->{
            String[] s=key.split("_");
            String eventId = s[0] + '_' + s[1] + '_' + System.currentTimeMillis();
            emitterRepository.saveEventCache(eventId, num);
            try {
                value.send(SseEmitter.event()
                        .id(eventId)
                        .data(num)
                );
            } catch (IOException e) {
                emitterRepository.deleteById(key);
            }
        });
    }

    public SseEmitter participate(Long id, String userId, String pw, String otp, String lastEventId, Group group, Long timetable_id){


        //참여자수+1 업데이트 하기
        group.increaseNum(group);
        groupRepository.save(group);

        //생성자 객체 찾아 그룹 멤버에 PARTICIPANT 넣기
        Member member=memberRepository.findByUserIdAndPw(userId, pw);

        if(member==null){
            throw new LoginException("로그인 오류");
        }

        GroupMember groupMember = GroupMember.create(member, group, Position.PARTICIPANT, timetable_id);
        groupMemberRepository.save(groupMember);


        return subscribe(group.getId(), id, lastEventId, group.getNum());
    }

    public void cancel(Long groupId, String groupName){
        Group group = groupRepository.findByIdAndName(groupId, groupName);

        //Group, Group member Repository 삭제
        deleteGroupMemberByGroup(group);
        groupRepository.deleteById(groupId);

        //sseEmitter, cache 삭제
        emitterRepository.deleteEmitterStartWithById(String.valueOf(groupId));
        emitterRepository.deleteEventCacheStartWithById(String.valueOf(groupId));
    }

    private void deleteGroupMemberByGroup(Group group){
        List<GroupMember> list = groupMemberRepository.findAllByGroup(group);

        list.forEach((entry)->{
            entry.setMember(null);
        });
    }

    public void out(Member m, Group g){
        Member member = memberRepository.findByUserIdAndPw(m.getUserId(), m.getPw());
        Group group = groupRepository.findByIdAndName(g.getId(), g.getName());

        GroupMember groupMember = groupMemberRepository.findByGroupAndMember(group, member);

        if(groupMember!=null){
            groupMember.setGroup(null);
            groupMember.setMember(null);
            groupMemberRepository.deleteById(groupMember.getId());

            //참여자 수 1 감소
            group.decreaseNum(group);
            groupRepository.save(group);

            //sseEmitter, cache 삭제
            emitterRepository.deleteEmitterStartWithById(group.getId() + "_" + m.getId());
            emitterRepository.deleteEventCacheStartWithById(group.getId() + "_" + m.getId());
            //다른 참여자들에게 참여자 수 다시 전달
            sendToAllGroupMember(group.getId(),group.getNum());
        }
        else{
            throw new GroupException("그룹 멤버 없음");
        }
    }

    public TimetableForm create(List<Schedule> scheduleList){

        String mon = findTimeOnDay(DayOfWeek.MON, scheduleList);
        String tue = findTimeOnDay(DayOfWeek.TUE, scheduleList);
        String wed = findTimeOnDay(DayOfWeek.WED, scheduleList);
        String thu = findTimeOnDay(DayOfWeek.THU, scheduleList);
        String fri = findTimeOnDay(DayOfWeek.FRI, scheduleList);
        String sat = findTimeOnDay(DayOfWeek.SAT, scheduleList);
        String sun = findTimeOnDay(DayOfWeek.SUN, scheduleList);

        TimetableForm result=new TimetableForm();
        result.setMon(mon);
        result.setTue(tue);
        result.setWed(wed);
        result.setThu(thu);
        result.setFri(fri);
        result.setSat(sat);
        result.setSun(sun);

        return result;
    }

    public String findTimeOnDay(DayOfWeek day, List<Schedule> scheduleList) {
        Stack<String> stack = new Stack<>();
        String ans="";

        stack.push("0000");

        //1. stack >= start time일 경우, stack < endtime일 경우, stack을 endtime으로 바꾼다.
        //2. stack < start time일 경우, string 값으로 저장한다. stack, start time 한다. stack의 값을 pop하고 end time을 push 한다.
        //3. 맨 마지막에 2400으로 끝낸다.

        for(Schedule schedule:scheduleList){
            if(schedule.getDayOfWeek()!=day){
                continue;
            }
            //System.out.println("ok");

            String s = stack.peek();
            String startTime=schedule.getStartTime();
            String endTime=schedule.getEndTime();

            if(s.compareTo(startTime)>=0){
                if(s.compareTo(endTime)<0){
                    stack.pop();
                    stack.push(endTime);
                }
            }
            else{
                ans=ans+s+"~"+startTime+"%";
                stack.pop();
                stack.push(endTime);
            }
        }

        String s = stack.peek();
        if(s.compareTo("2400")<0){
            ans=ans+s+"~2400";
            stack.pop();
        }

        return ans;
    }

    public HttpSession sessionValid(HttpServletRequest request){
        HttpSession session=request.getSession(false);

        if(session==null){
            throw new SessionExpireException("세션이 만료되었습니다.");
        }

        return session;
    }

    public Member memberValid(HttpSession session){
        Member m = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);
        if(m==null){
            throw new SessionExpireException("세션이 만료되었습니다.");
        }

        Member member=memberRepository.findByUserIdAndPw(m.getUserId(), m.getPw());

        if(member==null){
            throw new SessionExpireException("멤버 오류");
        }

        return member;
    }

    public Group groupValid(HttpSession session){
        Group group = (Group) session.getAttribute(SessionConstants.GROUP);
        if(group==null){
            throw new GroupException("그룹 세션 오류");
        }

        Group result = groupRepository.findByIdAndName(group.getId(), group.getName());

        if(result==null){
            throw new GroupException("알수없는 그룹");
        }

        return result;
    }

    public void tableValid(Member member, Long tableId){
        boolean check=timetableRepository.existsByMemberAndId(member,tableId);

        if(!check){
            throw new GroupException("유효하지 않은 table");
        }
    }
}
