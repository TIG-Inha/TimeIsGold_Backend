package TimeIsGold.TimeIsGold.domain.group;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@NoArgsConstructor
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
    public void deleteEventCacheById(String id) {
        eventCache.remove(id);
    }

    @Override
    public void deleteEmitterStartWithByGroup(Long groupId){
        Map<String, SseEmitter> deleteList = findAllEmitterStartWithById(String.valueOf(groupId));

        deleteList.forEach((key, value)->{
            deleteById(key);
        });
    }

    @Override
    public void deleteEventCacheStartWithByGroup(Long groupId){
        Map<String, Long> deleteList = findAllEventCacheStartWithById(String.valueOf(groupId));

        deleteList.forEach((key, value)->{
            deleteEventCacheById(key);
        });
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

    @Override
    public Integer countEmitter(){
        return emitters.size();
    }

    @Override
    public Integer countEventCache(){
        return eventCache.size();
    }
}
