package TimeIsGold.TimeIsGold.domain.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {

    //Map<String, Object> findAllEventCacheStartsWithId(String valueOf);

    SseEmitter save(String id, SseEmitter sseEmitter);
    void saveEventCache(String emitterId, Object event);

    void deleteById(String id);

    Map<String, SseEmitter> findAllEmitterStartWithById(String id);
    Map<String, Object> findAllEventCacheStartWithById(String id);

}
