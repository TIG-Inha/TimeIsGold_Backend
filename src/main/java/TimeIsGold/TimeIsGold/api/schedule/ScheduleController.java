package TimeIsGold.TimeIsGold.api.schedule;

import TimeIsGold.TimeIsGold.api.ApiResponse;
import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleAddForm;
import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleDeleteForm;
import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleShowResponse;
import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleUpdateForm;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.schedule.Schedule;
import TimeIsGold.TimeIsGold.domain.schedule.ScheduleRepository;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import TimeIsGold.TimeIsGold.exception.schedule.ScheduleException;
import TimeIsGold.TimeIsGold.service.schedule.ScheduleService;
import TimeIsGold.TimeIsGold.validation.TimeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public ApiResponse add(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = true) Member loginMember,
            @RequestBody @Valid ScheduleAddForm request){

        String startTime = request.getStartTime();
        String endTime = request.getEndTime();

        // 시간 포맷 검증
        boolean startMatches = TimeValidator.matches(startTime);
        boolean endMatches = TimeValidator.matches(endTime);
        if(!startMatches || !endMatches){
            throw new ScheduleException("잘못된 시각 입력");
        }

        // endTime 이 startTime 보다 뒤에 있는지 체크
        if(Integer.parseInt(startTime) >= Integer.parseInt(endTime)){
            throw new ScheduleException("시작시각은 종료시각 보다 빨라야 합니다.");
        }

        // 유저가 시간표를 가지고 있는지 체크
        loginMember.getTimetables().stream()
                .filter(t -> t.getId().equals( request.getTimetableId()))
                .findFirst().orElseThrow(() -> {
                    throw new ScheduleException("존재하지 않는 시간표 입니다.");
                });

        // 스케줄 추가
        scheduleService.add(request);

        return ApiResponse.createSuccessWithOutContent();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update")
    public ApiResponse update(@SessionAttribute(value = SessionConstants.LOGIN_MEMBER, required = true) Member loginMember,
                              @RequestBody @Valid ScheduleUpdateForm form){

        log.info("request form: {}", form);

        String startTime = form.getStartTime();
        String endTime = form.getEndTime();

        // 시간 포맷 검증
        boolean startMatches = TimeValidator.matches(startTime);
        boolean endMatches = TimeValidator.matches(endTime);
        if(!startMatches || !endMatches){
            throw new ScheduleException("잘못된 시각 입력");
        }

        // endTime 이 startTime 보다 뒤에 있는지 체크
        if(Integer.parseInt(startTime) >= Integer.parseInt(endTime)){
            throw new ScheduleException("시작시각은 종료시각 보다 빨라야 합니다.");
        }

        // schedule 추가할 timetable 찾기(login member의 timetable 인지 확인)
        Timetable timetable = loginMember.getTimetables().stream()
                .filter(t -> t.getId().equals(form.getTimetableId()))
                .findFirst().orElseThrow(() -> {
                    throw new ScheduleException("존재하지 않는 시간표 입니다.");
                });

//        log.info("scheduleId from request form: {}", form.getScheduleId());

        Schedule updateSchedule = scheduleService.update(form);

        ScheduleShowResponse result = new ScheduleShowResponse(updateSchedule.getId(), updateSchedule.getName(),
                updateSchedule.getDayOfWeek(), updateSchedule.getStartTime(), updateSchedule.getEndTime());

        return ApiResponse.createSuccess(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete")
    public ApiResponse delete(@SessionAttribute(value = SessionConstants.LOGIN_MEMBER, required = true) Member loginMember,
                              @RequestBody @Valid ScheduleDeleteForm form) {

        scheduleService.deleteSchedule(loginMember, form);

        return ApiResponse.createSuccessWithOutContent();
    }
}