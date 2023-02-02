package TimeIsGold.TimeIsGold.api.schedule.dto;

import TimeIsGold.TimeIsGold.domain.schedule.DayOfWeek;
import TimeIsGold.TimeIsGold.validation.Enum.ValidEnum;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class ScheduleAddRequest {

    @NotBlank
    private String name;

    @ValidEnum(enumClass = DayOfWeek.class)
    private DayOfWeek dayOfWeek;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    @Min(0)
    private Long timetableId;
}
