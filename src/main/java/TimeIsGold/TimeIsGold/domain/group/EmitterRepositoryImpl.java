package TimeIsGold.TimeIsGold.domain.group;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EmitterRepositoryImpl implements EmitterRepository{
    //동시성 고려를 위해 ConcurrentHashMap 사용
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Long> eventCache = new ConcurrentHashMap<>(); // eventId, 참여자 수

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String eventCacheId, Long event) {
        eventCache.put(eventCacheId, event);
    }

    @Override
    public void deleteById(String id) {
        emitters.remove(id);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithById(String id) {
        return emitters.entrySet().stream()
                .filter(entry->entry.getKey().startsWith(id))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Long> findAllEventCacheStartWithById(String id) {
        return eventCache.entrySet().stream()
                .filter(entry->entry.getKey().startsWith(id))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
