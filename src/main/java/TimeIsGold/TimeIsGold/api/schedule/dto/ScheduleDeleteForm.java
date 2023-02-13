package TimeIsGold.TimeIsGold.api.schedule.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ScheduleDeleteForm {

    @Min(0)
    private Long timetableId;

    @Min(0)
    private Long scheduleId;
}
