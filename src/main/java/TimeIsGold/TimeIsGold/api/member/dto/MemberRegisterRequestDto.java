package TimeIsGold.TimeIsGold.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberRegisterRequestDto {

    public String id;
    public String pw;
    public String pwCheck;
    public String name;
    public String duplicatedIdCheck;
}
