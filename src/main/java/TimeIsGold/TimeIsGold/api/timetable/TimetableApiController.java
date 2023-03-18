package TimeIsGold.TimeIsGold.api.timetable;

import TimeIsGold.TimeIsGold.api.ApiResponse;
import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.api.schedule.dto.ScheduleShowResponse;
import TimeIsGold.TimeIsGold.api.timetable.dto.CreateTimetableRequest;
import TimeIsGold.TimeIsGold.api.timetable.dto.TimetableShowAllResponse;
import TimeIsGold.TimeIsGold.api.timetable.dto.TimetableShowResponse;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableRepository;
import TimeIsGold.TimeIsGold.exception.timetable.NoSuchTimetableFound;
import TimeIsGold.TimeIsGold.service.timetable.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timetables")
public class TimetableApiController {

    private final TimetableRepository timetableRepository;
    private final TimetableService timetableService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add")
    public ApiResponse create(@ApiIgnore @SessionAttribute(name = SessionConstants.LOGIN_MEMBER) Member loginMember,
                              @RequestBody @Valid CreateTimetableRequest request) {

        String name = request.getName();
        timetableService.create(loginMember, name);

        return ApiResponse.createSuccessWithOutContent();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public ApiResponse showAll(@ApiIgnore @SessionAttribute(name = SessionConstants.LOGIN_MEMBER) Member loginMember) {

        List<TimetableShowAllResponse> responses = new ArrayList<>();
        loginMember.getTimetables().forEach(t -> {
            TimetableShowAllResponse response = new TimetableShowAllResponse(t.getId(), t.getName());
            responses.add(response);
        });

        return ApiResponse.createSuccess(responses);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{timetableId}")
    public ApiResponse show(@ApiIgnore @SessionAttribute(name = SessionConstants.LOGIN_MEMBER) Member loginMember,
                            @PathVariable Long timetableId) {


        // 경로변수로 받은 timetableId 로 시간표 조회하기
        Timetable findTimetable = timetableRepository.findById(timetableId).orElseThrow(() -> {
            throw new NoSuchTimetableFound("존재하지 않는 시간표입니다.");
        });

        // 다른 사용자의 시간표를 접근하려 할때 처리
        loginMember.getTimetables().stream()
                .filter(t -> t.getId().equals(findTimetable.getId()))
                .findFirst().orElseThrow(() -> {
                    throw new NoSuchTimetableFound("유효하지 않은 접근입니다.");
                });

        String name = findTimetable.getName();
        List<ScheduleShowResponse> scheduleShowResponses = new ArrayList<>();
        findTimetable.getSchedules().forEach(s -> {
            scheduleShowResponses.add(new ScheduleShowResponse(s.getId(), s.getName(), s.getDayOfWeek(),
                    s.getStartTime(), s.getEndTime()));
        });

        TimetableShowResponse response = new TimetableShowResponse(name, scheduleShowResponses);
        return ApiResponse.createSuccess(response);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete")
    public ApiResponse delete() {



        return ApiResponse.createSuccessWithOutContent();
    }
}
