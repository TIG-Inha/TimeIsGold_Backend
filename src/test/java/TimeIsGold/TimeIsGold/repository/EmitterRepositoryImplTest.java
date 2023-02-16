package TimeIsGold.TimeIsGold.repository;

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
        String emitterId=groupId+"_"+memberId+"_"+System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        Assertions.assertDoesNotThrow(() -> emitterRepository.save(emitterId, sseEmitter));
    }

    @Test
    @DisplayName("새로운 event를 추가한다.")
    public void saveEventCache() throws Exception{
        Long memberId=1L;
        Long groupId=1L;
        String eventCacheId=groupId+"_"+memberId+"_"+System.currentTimeMillis();

        Assertions.assertDoesNotThrow(() -> emitterRepository.saveEventCache(eventCacheId, 1L));
    }

    @Test
    @DisplayName("어떤 회원이 접속한 Emitter와 특정 그룹일 때 Emitter를 찾는다.")
    public void findAllEmitterStartWithById() throws Exception{
        Long memberId1=1L;
        Long groupId=1L;
        String emitterId1 = groupId + "_" + memberId1 + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        Long memberId2=2L;
        String emitterId2 = groupId + "_" + memberId2 + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        Long memberId3=3L;
        String emitterId3 = groupId + "_" + memberId3 + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId3, new SseEmitter(DEFAULT_TIMEOUT));

        //앞에 groupId가 같을 때
        Map<String, SseEmitter> result1=emitterRepository.findAllEmitterStartWithById(String.valueOf(groupId));
        Assertions.assertEquals(3, result1.size());

        //groupId와 memeberId 둘 다 같을 때
        Map<String, SseEmitter> result2=emitterRepository.findAllEmitterStartWithById(groupId + "_" + memberId1);
        Assertions.assertEquals(1, result2.size());
    }

    @Test
    @DisplayName("특정 그룹에 속한 어떤 회원에게 수신된 이벤트를 캐시에서 모두 찾는다.")
    public void findAllCacheStartWithById() throws Exception{
        Long memberId=1L;
        Long groupId=1L;
        String emitterId1 = groupId + "_" + memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = groupId + "_" + memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId3 = groupId + "_" + memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId3, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId4 = 2L + "_" + memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId4, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId5 = groupId + "_" + 2L + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId4, new SseEmitter(DEFAULT_TIMEOUT));


        //groupId와 memeberId 둘 다 같을 때
        Map<String, SseEmitter> result=emitterRepository.findAllEmitterStartWithById(groupId + "_" + memberId);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    @DisplayName("ID를 통해 Emitter를 Repository에서 제거한다.")
    public void deleteById() throws Exception {
        //given
        Long memberId = 1L;
        String emitterId =  memberId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        //when
        emitterRepository.save(emitterId, sseEmitter);
        emitterRepository.deleteById(emitterId);

        //then
        Assertions.assertEquals(0, emitterRepository.findAllEmitterStartWithById(emitterId).size());
    }


}
