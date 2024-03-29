package TimeIsGold.TimeIsGold.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdCheckResponseDto {

    // true: 사용가능, false: 사용불가
    private HttpStatus httpStatus;
    private String message;
}
