package TimeIsGold.TimeIsGold.repository;

import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.member.MemberRepository;
import TimeIsGold.TimeIsGold.domain.schedule.DayOfWeek;
import TimeIsGold.TimeIsGold.domain.schedule.Schedule;
import TimeIsGold.TimeIsGold.domain.schedule.ScheduleRepository;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class ScheduleRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TimetableRepository timetableRepository;

    @Test
    @DisplayName("findAllByTableIdInOrderByStartTimeAsc가 잘 작동되는지 확인한다.")
    public void findAllByTableIdInOrderByStartTimeAsc() {
        Member member1=Member.create("1","1","1");
        Member member2=Member.create("2","1","1");
        Member member3=Member.create("3","1","1");
        Member member4=Member.create("4","1","1");
        Member member5=Member.create("5","1","1");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        Timetable timetable1=Timetable.create(member1,"1");
        Timetable timetable2=Timetable.create(member1,"2");
        Timetable timetable3=Timetable.create(member1,"3");
        Timetable timetable4=Timetable.create(member1,"4");
        Timetable timetable5=Timetable.create(member1,"5");

        timetableRepository.save(timetable1);
        timetableRepository.save(timetable2);
        timetableRepository.save(timetable3);
        timetableRepository.save(timetable4);
        timetableRepository.save(timetable5);

        Schedule schedule1 = Schedule.create("1", DayOfWeek.FRI, "0900", "1000", timetable1);
        Schedule schedule2 = Schedule.create("1", DayOfWeek.FRI, "1100", "1200", timetable2);
        Schedule schedule3 = Schedule.create("1", DayOfWeek.FRI, "0900", "0930", timetable3);
        Schedule schedule4 = Schedule.create("1", DayOfWeek.FRI, "1300", "1400", timetable1);


        List<Long> find=new ArrayList<>();
        find.add(timetable1.getId());
        find.add(timetable2.getId());
        find.add(timetable3.getId());

        List<Schedule> schedule=scheduleRepository.findAllByTableIdInOrderByStartTimeAsc(find);

        Assertions.assertEquals(schedule.size(),4);
    }
}
