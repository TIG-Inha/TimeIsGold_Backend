package TimeIsGold.TimeIsGold.service.schedule;

import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleAddForm;
import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleDeleteForm;
import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleShowResponse;
import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleUpdateForm;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.schedule.Schedule;
import TimeIsGold.TimeIsGold.domain.schedule.ScheduleRepository;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableRepository;
import TimeIsGold.TimeIsGold.exception.schedule.ScheduleException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TimetableRepository timetableRepository;

//    scheduleId가 존재하면 update, scheduleId == null이면 퍼시스트
    @Transactional
    public Long add(ScheduleAddForm request) {

        String startTime = request.getStartTime();
        String endTime = request.getEndTime();

        Timetable timetable = timetableRepository.findById(request.getTimetableId()).orElseThrow(() -> {
            throw new ScheduleException("존재하지 않는 시간표입니다.");
        });

        // 시간 겹치는지 체크
        timetable.getSchedules().forEach(s -> {
            if(checkTimeDuplication(startTime, endTime, s.getStartTime(), s.getEndTime())){
                throw new ScheduleException("같은 시간에 이미 일정이 존재합니다.");
            }
        });

        Schedule schedule = Schedule.create(
                request.getName(), request.getDayOfWeek(), request.getStartTime(),
                request.getEndTime(), timetable);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return savedSchedule.getId();
    }

    @Transactional
    public Schedule update(ScheduleUpdateForm form){

        String startTime = form.getStartTime();
        String endTime = form.getEndTime();

        // 해당 schedule 이 존재하는지
        Schedule findSchedule = scheduleRepository.findById(form.getScheduleId()).orElseThrow(() -> {
            throw new ScheduleException("존재하지 않는 일정입니다.");
        });

        Timetable timetable = timetableRepository.findById(form.getTimetableId()).orElseThrow(() -> {
            throw new ScheduleException("존재하지 않는 시간표 입니다.");
        });

        // 해당 timetable 에 속해 있는지 체크
        if(!findSchedule.getTimetable().equals(timetable)){
            throw new ScheduleException("다른테이블에 존재하는 일정을 변경하는 요청입니다.");
        }

        // 시간 겹치는지 체크
        timetable.getSchedules().stream()
                .filter(s -> !s.getId().equals(form.getScheduleId()))
                .forEach(s -> {
                    if(checkTimeDuplication(startTime, endTime, s.getStartTime(), s.getEndTime())){
                        log.error("checkTimeDuplication : {}", checkTimeDuplication(startTime, endTime, s.getStartTime(), s.getEndTime()));
                        throw new ScheduleException("같은 시간에 이미 일정이 존재합니다.");
                    }
                });

        findSchedule.update(form.getName(), form.getDayOfWeek(), form.getStartTime(), form.getEndTime());

        return findSchedule;
    }

    //schedule repo 에서 delete 시 timetable 테이블에 update 쿼리 날아가는지 체크할 것
    @Transactional
    public void deleteSchedule(Member member, ScheduleDeleteForm form) {

        // 시간표 조회
        Timetable findTImetable = timetableRepository.findById(form.getTimetableId()).orElseThrow(() -> {
            throw new ScheduleException("존재하지 않는 시간표 입니다.");
        });

        // 시간표가 본인 것인지 확인
        if (findTImetable.getMember().getId() != member.getId()) {
            throw new ScheduleException("잘못된 접근 입니다.");
        }

        // 해당 schedule 이 존재하는지
        Schedule findSchedule = scheduleRepository.findById(form.getScheduleId()).orElseThrow(() -> {
            throw new ScheduleException("존재하지 않는 일정입니다.");
        });

        // 스케줄이 시간표에 속하는지 확인
        if (findSchedule.getTimetable().getId() != findTImetable.getId()) {
            throw new ScheduleException("잘못된 접근 입니다.");
        }

        scheduleRepository.deleteById(findSchedule.getId());
        findTImetable.deleteSchedule(findSchedule.getId());
    }


    private Boolean checkTimeDuplication(String startTime, String endTime, String compSt, String compEt){

        Integer start0 = Integer.parseInt(startTime);
        Integer end0 = Integer.parseInt(endTime);

        Integer start1 = Integer.parseInt(compSt);
        Integer end1 = Integer.parseInt(compEt);
        return !((start0 <= start1 && end0 <= start1) || (start0 >= end1 && end0 >= end1));
    }
}
