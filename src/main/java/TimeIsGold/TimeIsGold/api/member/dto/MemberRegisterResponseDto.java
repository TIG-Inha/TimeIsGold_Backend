package TimeIsGold.TimeIsGold.api.member.dto;

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
