package TimeIsGold.TimeIsGold.api.schedule.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ScheduleAddRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String startTime;

    @NotEmpty
    private String endTime;
}
