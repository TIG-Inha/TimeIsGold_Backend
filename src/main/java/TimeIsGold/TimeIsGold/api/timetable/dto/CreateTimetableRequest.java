package TimeIsGold.TimeIsGold.api.timetable.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

// request dto 의 경우 reflection 을 위해 기본 생성자가 필요함 -> reflection 이 뭔데?
@Data
@NoArgsConstructor
public class CreateTimetableRequest {

    @NotBlank
    private String name;
}
