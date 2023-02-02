package TimeIsGold.TimeIsGold.api.schedule.dto;

import TimeIsGold.TimeIsGold.domain.schedule.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleShowResponse {

    private String name;
    private DayOfWeek dayOfWeek;
    private String startTime;
    private String endTime;
}
