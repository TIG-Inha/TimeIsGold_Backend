package TimeIsGold.TimeIsGold.service.group;

import TimeIsGold.TimeIsGold.domain.group.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
//import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private EmitterRepository emitterRepository;

    private String includeTime(Long groupId, Long userId){
        String id=groupId+"_"+userId+"_"+System.currentTimeMillis();

        return id;
    }

    public SseEmitter start(Long groupId, Long userId, String lastEventId){
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
        sendToClient(sseEmitter, emitterId, eventId, 1L);

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

    /*
    public String createOTP(){
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for(int i=0;i<8;i++){
            int index=rnd.nextInt(3);

            switch (index){
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }
    */

}
