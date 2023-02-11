package TimeIsGold.TimeIsGold.api.group.dto;

import lombok.AllArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@AllArgsConstructor
public class GroupStartResponseDto {
    String otp;
    SseEmitter emit;
}
