package TimeIsGold.TimeIsGold.api.timetable;

import TimeIsGold.TimeIsGold.api.ApiResponse;
import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.api.timetable.dto.CreateTimetableRequest;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableRepository;
import TimeIsGold.TimeIsGold.service.timetable.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timetables")
public class TimetableApiController {

    private final TimetableRepository timetableRepository;
    private final TimetableService timetableService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add")
    public ApiResponse create(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER) Member loginMember,
                              @RequestBody @Valid CreateTimetableRequest request){

        String name = request.getName();
        timetableService.create(loginMember, name);

        return ApiResponse.createSuccessWithOutContent();
    }
}
