package TimeIsGold.TimeIsGold.service.schedule;

import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleAddRequest;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.schedule.Schedule;
import TimeIsGold.TimeIsGold.domain.schedule.ScheduleRepository;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableRepository;
import TimeIsGold.TimeIsGold.exception.schedule.InvalidScheduleTimeException;
import TimeIsGold.TimeIsGold.validation.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TimetableRepository timetableRepository;

//    scheduleId가 존재하면 update, scheduleId == null이면 퍼시스트
    @Transactional
    public Long add(Timetable timetable, ScheduleAddRequest request) {

        Schedule schedule = Schedule.create(
                request.getName(), request.getDayOfWeek(), request.getStartTime(),
                request.getEndTime(), timetable);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return savedSchedule.getId();
    }

//    @Transactional
//    public void updateSchedule(Long scheduleId, Member member,String scheduleName, Integer time) {
//        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
//        // 널체크(파라미터로 받은 scheduleId에 대한 스케줄 없을 때)
//        // 예외처리(겹치는 스케줄이 존재 할 때)
//
//        Schedule findSchedule = scheduleOptional.get();
//        findSchedule.setMember(member);
//        findSchedule.setScheduleName(scheduleName);
//        findSchedule.setTime(time);
//        scheduleRepository.save(findSchedule);
//    }

    //schedule repo 에서 delete 시 timetable 테이블에 update 쿼리 날아가는지 체크할 것
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }


}
