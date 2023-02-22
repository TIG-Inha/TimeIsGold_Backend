package TimeIsGold.TimeIsGold.domain.group;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;


public interface EmitterRepository {

    //Map<String, Object> findAllEventCacheStartsWithId(String valueOf);

    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    void saveEventCache(String eventId, Long event);

    void deleteById(String id);
    void deleteEventCacheById(String id);

    void deleteEmitterStartWithById(String id);
    void deleteEventCacheStartWithById(String id);

    Map<String, SseEmitter> findAllEmitterStartWithById(String id);
    Map<String, Long> findAllEventCacheStartWithById(String id);

    Integer countEmitter();
    Integer countEventCache();

}
