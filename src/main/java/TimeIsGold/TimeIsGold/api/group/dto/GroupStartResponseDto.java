package TimeIsGold.TimeIsGold.api.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@AllArgsConstructor
@Data
public class GroupStartResponseDto {
    String otp;
    SseEmitter emit;
}
