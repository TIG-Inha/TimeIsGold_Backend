package TimeIsGold.TimeIsGold.api.schedule;

import TimeIsGold.TimeIsGold.api.ApiResponse;
import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleAddRequest;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
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
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add")
    public ApiResponse add(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = true) Member loginMember,
            @RequestBody @Valid ScheduleAddRequest request){

        // startTime, endTime 검증
        boolean startMatches = TimeValidator.matches(request.getStartTime());
        boolean endMatches = TimeValidator.matches(request.getEndTime());
        if(!startMatches || !endMatches){
            throw new RuntimeException("잘못된 시각 입력");
        }

        // 기존 스케줄과 겹치는지 확인


        // 스케줄 추가
//        Timetable timetable = loginMember.getTimetable();
//        scheduleService.add(timetable, request);

        return ApiResponse.createSuccessWithOutContent();
    }
}
