package TimeIsGold.TimeIsGold.api.group.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class GroupStartRequestDto {

    @NotBlank
    @NotEmpty
    public String groupName;
}
