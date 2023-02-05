package TimeIsGold.TimeIsGold.api.schedule.dto;

import TimeIsGold.TimeIsGold.domain.schedule.DayOfWeek;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ScheduleUpdateForm {

    @Min(0)
    private Long scheduleId;

    @NotBlank
    private String name;

    private DayOfWeek dayOfWeek;

    private String startTime;

    private String endTime;

    private Long timetableId;
}
