package TimeIsGold.TimeIsGold.domain.schedule;

import TimeIsGold.TimeIsGold.domain.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByTableIdInOrderByStartTimeAsc(List<Long> timetableId);


}
