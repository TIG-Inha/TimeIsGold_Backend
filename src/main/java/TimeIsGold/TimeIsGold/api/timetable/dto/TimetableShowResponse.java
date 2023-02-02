package TimeIsGold.TimeIsGold.api.timetable.dto;

import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleShowResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TimetableShowResponse {

    private String name;

    private List<ScheduleShowResponse> schedules = new ArrayList<>();
}
