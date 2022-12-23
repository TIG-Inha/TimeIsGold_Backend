package TimeIsGold.TimeIsGold.controller.memberRegisterDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    // true: 사용가능, false: 사용불가
    private HttpStatus httpStatus;
    private String message;
}
