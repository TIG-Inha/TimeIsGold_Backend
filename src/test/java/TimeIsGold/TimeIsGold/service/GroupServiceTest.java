package TimeIsGold.TimeIsGold.service;

import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.member.MemberRepository;
import TimeIsGold.TimeIsGold.domain.schedule.DayOfWeek;
import TimeIsGold.TimeIsGold.domain.schedule.Schedule;
import TimeIsGold.TimeIsGold.domain.schedule.ScheduleRepository;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableForm;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableRepository;
import TimeIsGold.TimeIsGold.service.group.GroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private TimetableRepository timetableRepository;
    @InjectMocks
    private GroupService groupService;

    @Test
    @DisplayName("findTimeOnDay가 잘 작동되는지 확인한다.")
    public void findTimeOnDay() {
        Schedule schedule1 = Schedule.create("1", DayOfWeek.FRI, "0900", "1000");
        Schedule schedule2 = Schedule.create("1", DayOfWeek.FRI, "1100", "1200");
        Schedule schedule3 = Schedule.create("1", DayOfWeek.FRI, "0900", "0930");
        Schedule schedule4 = Schedule.create("1", DayOfWeek.FRI, "1300", "1400");



        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);
        scheduleList.add(schedule3);
        scheduleList.add(schedule4);


        String result = groupService.findTimeOnDay(DayOfWeek.FRI, scheduleList);

        String ans="0000~0900%1000~1100%1200~1300%1400~2400";

        Assertions.assertEquals(ans, result);
    }

    @Test
    @DisplayName("create가 잘 작동되는지 확인한다.")
    public void create() {
        Schedule schedule1 = Schedule.create("1", DayOfWeek.FRI, "0900", "1000");
        Schedule schedule2 = Schedule.create("1", DayOfWeek.FRI, "1100", "1200");
        Schedule schedule3 = Schedule.create("1", DayOfWeek.MON, "0900", "0930");
        Schedule schedule4 = Schedule.create("1", DayOfWeek.MON, "1300", "1400");
        Schedule schedule5 = Schedule.create("1", DayOfWeek.FRI, "0900", "0930");
        Schedule schedule6 = Schedule.create("1", DayOfWeek.FRI, "1300", "1400");


        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);
        scheduleList.add(schedule3);
        scheduleList.add(schedule4);
        scheduleList.add(schedule5);
        scheduleList.add(schedule6);


        TimetableForm result = groupService.create(scheduleList);
        TimetableForm ans = new TimetableForm();

        ans.setMon("0000~0900%0930~1300%1400~2400");
        ans.setTue("0000~2400");
        ans.setWed("0000~2400");
        ans.setThu("0000~2400");
        ans.setFri("0000~0900%1000~1100%1200~1300%1400~2400");
        ans.setSat("0000~2400");
        ans.setSun("0000~2400");

        Assertions.assertEquals(ans.getMon(), result.getMon());
        Assertions.assertEquals(ans.getTue(), result.getTue());
        Assertions.assertEquals(ans.getWed(), result.getWed());
        Assertions.assertEquals(ans.getThu(), result.getThu());
        Assertions.assertEquals(ans.getFri(), result.getFri());
        Assertions.assertEquals(ans.getSat(), result.getSat());
        Assertions.assertEquals(ans.getSun(), result.getSun());
    }
}
