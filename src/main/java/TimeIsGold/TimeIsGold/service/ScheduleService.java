package TimeIsGold.TimeIsGold.service;

import TimeIsGold.TimeIsGold.domain.Member;
import TimeIsGold.TimeIsGold.domain.Schedule;
import TimeIsGold.TimeIsGold.repository.ScheduleRepository;
import TimeIsGold.TimeIsGold.repository.TimetableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TimetableRepository timetableRepository;

    //scheduleId가 존재하면 update, scheduleId == null이면 퍼시스트
    @Transactional
    public Long createSchedule(Member member,String scheduleName, Integer time) {

        Schedule schedule = Schedule
                .createSchedule(member, scheduleName, time);
        scheduleRepository.save(schedule);

        return schedule.getId();
    }

    @Transactional
    public void updateSchedule(Long scheduleId, Member member,String scheduleName, Integer time) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
        // 널체크(파라미터로 받은 scheduleId에 대한 스케줄 없을 때)
        // 예외처리(겹치는 스케줄이 존재 할 때)

        Schedule findSchedule = scheduleOptional.get();
        findSchedule.setMember(member);
        findSchedule.setScheduleName(scheduleName);
        findSchedule.setTime(time);
        scheduleRepository.save(findSchedule);
    }

    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }


}
