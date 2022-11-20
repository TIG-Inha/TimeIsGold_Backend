package TimeIsGold.TimeIsGold.repository;

import TimeIsGold.TimeIsGold.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


}
