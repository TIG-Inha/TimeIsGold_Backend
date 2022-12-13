package TimeIsGold.TimeIsGold.controller.memberRegisterDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberRegisterResponseDto {

    private HttpStatus httpStatus;
    private Long memberId;
}
