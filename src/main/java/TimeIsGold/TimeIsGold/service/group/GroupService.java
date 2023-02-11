package TimeIsGold.TimeIsGold.service.group;

import TimeIsGold.TimeIsGold.domain.group.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private EmitterRepository emitterRepository;

    public SseEmitter start(Long userId, String lastEventId){
        //데이터가 유실된 시점을 파악하기 위해
        String id=userId+"_"+System.currentTimeMillis();

        //유효 시간 주기
        SseEmitter sseEmitter=new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(id ,sseEmitter);

        //SseEmitter의 시간 초과 및 네트워크 오류를 포함한 모든 이유로 비동기 요청이 정상 동작할 수 없다면 저장해둔 SseEmitter를 삭제한다.
        sseEmitter.onCompletion(() -> emitterRepository.deleteById(id));
        sseEmitter.onTimeout(() -> emitterRepository.deleteById(id));

        //SseEmiiter가 생성되면 더미 데이터를 보내야 함, 하나의 데이터도 전송되지 않는다면 유효 시간이 끝날 때 503 응답 발생
        sendToClient(sseEmitter, id, "EventStream Created. [userId=" + userId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(userId));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(sseEmitter, entry.getKey(), entry.getValue()));
        }
        return sseEmitter;
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
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
