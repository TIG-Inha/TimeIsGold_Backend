package TimeIsGold.TimeIsGold.domain;

import TimeIsGold.TimeIsGold.domain.group.EmitterRepository;
import TimeIsGold.TimeIsGold.domain.group.EmitterRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public class EmitterRepositoryImplTest {

    private EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private Long DEFAULT_TIMEOUT=60L*1000L*60L;

    @Test
    @DisplayName("새로운 Emitter를 추가한다.")
    public void save() throws Exception{
        Long memberId=1L;
        Long groupId=1L;
        String emitterId=groupId+"_"+memberId+System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        Assertions.assertDoesNotThrow(() -> emitterRepository.save(emitterId, sseEmitter));
    }

    @Test
    @DisplayName("어떤 회원이 접속한 모든 Emitter를 찾는다.")
    public void findAllEmitterStartWithByMemberId() throws Exception{
        Long memberId1=1L;
        Long groupId=1L;
        String emitterId1 = groupId + "_" + memberId1 + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Long memberId2=2L;
        String emitterId2 = groupId + "_" + memberId2 + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        Long memberId3=3L;
        String emitterId3 = groupId + "_" + memberId3 + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId3, new SseEmitter(DEFAULT_TIMEOUT));

        //앞에 groupId가 같을 때
        Map<String, SseEmitter> result1=emitterRepository.findAllEmitterStartWithById(String.valueOf(groupId));
        Assertions.assertEquals(3, result1.size());

        //groupId와 memeberId 둘 다 같을 때
        Map<String, SseEmitter> result2=emitterRepository.findAllEmitterStartWithById(String.valueOf(groupId+"_"+memberId1));
        Assertions.assertEquals(1, result2.size());


    }


}
