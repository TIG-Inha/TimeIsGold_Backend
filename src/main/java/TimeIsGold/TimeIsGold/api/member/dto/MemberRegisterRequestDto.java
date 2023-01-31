package TimeIsGold.TimeIsGold.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberRegisterRequestDto {

    @NotBlank
    public String id;

    @NotBlank
    public String pw;

    @NotBlank
    public String pwCheck;

    @NotBlank
    public String name;

    @NotBlank
    public String duplicatedIdCheck;
}
