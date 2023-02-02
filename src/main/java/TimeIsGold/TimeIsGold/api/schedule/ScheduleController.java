package TimeIsGold.TimeIsGold.api.schedule;

import TimeIsGold.TimeIsGold.api.ApiResponse;
import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleAddRequest;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import TimeIsGold.TimeIsGold.exception.schedule.InvalidScheduleTimeException;
import TimeIsGold.TimeIsGold.exception.timetable.NoSuchTimetableFound;
import TimeIsGold.TimeIsGold.service.schedule.ScheduleService;
import TimeIsGold.TimeIsGold.validation.TimeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add")
    public ApiResponse add(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = true) Member loginMember,
            @RequestBody @Valid ScheduleAddRequest request){

        String startTime = request.getStartTime();
        String endTime = request.getEndTime();

        // 시간 포맷 검증
        boolean startMatches = TimeValidator.matches(startTime);
        boolean endMatches = TimeValidator.matches(endTime);
        if(!startMatches || !endMatches){
            throw new RuntimeException("잘못된 시각 입력");
        }

        // endTime 이 startTime 보다 뒤에 있는지 체크
        if(Integer.parseInt(startTime) >= Integer.parseInt(endTime)){
            throw new RuntimeException("시작시각은 종료시각 보다 빨라야 합니다.");
        }

        // schedule 추가할 timetable 찾기
        Timetable timetable = loginMember.getTimetables().stream()
                .filter(t -> t.getId() == request.getTimetableId())
                .findFirst().orElseGet(() -> {
                    throw new RuntimeException("존재하지 않는 시간표 입니다.");
                });

        // 시간 겹치는지 체크
        timetable.getSchedules().forEach(s -> {
            if(checkTimeDuplication(startTime, endTime, s.getStartTime(), s.getEndTime())){
                log.error("checkTimeDuplication : {}", checkTimeDuplication(startTime, endTime, s.getStartTime(), s.getEndTime()));
                throw new RuntimeException("같은 시간에 이미 일정이 존재합니다.");
            }
        });


        // 스케줄 추가
        scheduleService.add(timetable, request);

        return ApiResponse.createSuccessWithOutContent();
    }

    private Boolean checkTimeDuplication(String startTime, String endTime, String compSt, String compEt){

        Integer start0 = Integer.parseInt(startTime);
        Integer end0 = Integer.parseInt(endTime);

        Integer start1 = Integer.parseInt(compSt);
        Integer end1 = Integer.parseInt(compEt);
        return !((start0 <= start1 && end0 <= start1) || (start0 >= end1 && end0 >= end1));
    }
}
