package TimeIsGold.TimeIsGold.service.group;

import TimeIsGold.TimeIsGold.domain.group.EmitterRepository;
import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.group.GroupRepository;
import TimeIsGold.TimeIsGold.domain.group.Position;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMember;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMemberRepository;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.member.MemberRepository;
import TimeIsGold.TimeIsGold.exception.group.GroupException;
import TimeIsGold.TimeIsGold.exception.login.LoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
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

    public SseEmitter participate(Long id, String userId, String pw, String otp, String lastEventId){
        //그룹 정보 불러오기
        Group group=groupRepository.findByOtp(otp);

        if(group==null){
            throw new GroupException("그룹이 유효하지 않습니다.");
        }

        //참여자수+1 업데이트 하기
        group.increaseNum(group);
        groupRepository.save(group);

        //생성자 객체 찾아 그룹 멤버에 PARTICIPANT 넣기
        Member member=memberRepository.findByUserIdAndPw(userId, pw);

        if(member==null){
            throw new LoginException("로그인 오류");
        }

        GroupMember groupMember = GroupMember.create(member, group, Position.PARTICIPANT);
        groupMemberRepository.save(groupMember);


        return subscribe(group.getId(), id, lastEventId, group.getNum());
    }

    public void cancel(Long groupId, String groupName){
        Group group = groupRepository.findByIdAndName(groupId, groupName);

        //Group, Group member Repository 삭제
        groupMemberRepository.deleteByGroup(group);
        groupRepository.deleteById(group.getId());

        //sseEmitter, cache 삭제
        emitterRepository.deleteEmitterStartWithByGroup(groupId);
        emitterRepository.deleteEventCacheStartWithByGroup(groupId);
    }
}
