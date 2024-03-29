package TimeIsGold.TimeIsGold.repository;

import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.group.GroupRepository;
import TimeIsGold.TimeIsGold.domain.group.Position;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMember;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMemberRepository;
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
public class GroupMemberRepositoryTest {

    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TimetableRepository timetableRepository;

    /*
    @Test
    @DisplayName("deleteByGroup가 잘 작동되는지 확인한다.")
    public void deleteByGroup(){
        Group group1 = Group.create("a", 1L);
        Group group2 = Group.create("a", 1L);

        Member member1=Member.create("1","1","1");
        Member member2=Member.create("2","1","1");
        Member member3=Member.create("3","1","1");
        Member member4=Member.create("4","1","1");
        Member member5=Member.create("5","1","1");

        groupRepository.save(group1);
        groupRepository.save(group2);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        GroupMember groupMember1 = GroupMember.create(member1, group1, Position.HOST);
        GroupMember groupMember2 = GroupMember.create(member2, group1, Position.PARTICIPANT);
        GroupMember groupMember3 = GroupMember.create(member3, group1, Position.PARTICIPANT);
        GroupMember groupMember4 = GroupMember.create(member4, group2, Position.HOST);
        GroupMember groupMember5 = GroupMember.create(member5, group2, Position.PARTICIPANT);

        groupMemberRepository.save(groupMember1);
        groupMemberRepository.save(groupMember2);
        groupMemberRepository.save(groupMember3);
        groupMemberRepository.save(groupMember4);
        groupMemberRepository.save(groupMember5);

        groupMemberRepository.deleteByGroup(group1);

        Assertions.assertEquals(groupMemberRepository.count(),2);
    }


    @Test
    @DisplayName("findAllByGroup가 잘 작동되는지 확인한다.")
    public void findAllByGroup(){
        Group group1 = Group.create("a", 1L);
        Group group2 = Group.create("a", 1L);

        Member member1=Member.create("1","1","1");
        Member member2=Member.create("2","1","1");
        Member member3=Member.create("3","1","1");
        Member member4=Member.create("4","1","1");
        Member member5=Member.create("5","1","1");

        groupRepository.save(group1);
        groupRepository.save(group2);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        GroupMember groupMember1 = GroupMember.create(member1, group1, Position.HOST);
        GroupMember groupMember2 = GroupMember.create(member2, group1, Position.PARTICIPANT);
        GroupMember groupMember3 = GroupMember.create(member3, group1, Position.PARTICIPANT);
        GroupMember groupMember4 = GroupMember.create(member4, group2, Position.HOST);
        GroupMember groupMember5 = GroupMember.create(member5, group2, Position.PARTICIPANT);

        groupMemberRepository.save(groupMember1);
        groupMemberRepository.save(groupMember2);
        groupMemberRepository.save(groupMember3);
        groupMemberRepository.save(groupMember4);
        groupMemberRepository.save(groupMember5);



        List<GroupMember> list=groupMemberRepository.findAllByGroup(group1);

        Assertions.assertEquals(list.size(),3);
    }

     */

    @Test
    @DisplayName("findAllTimetableIdByGroup가 잘 작동되는지 확인한다.")
    public void findAllTimetableIdByGroup(){
        Group group1 = Group.create("a", 1L);
        Group group2 = Group.create("a", 1L);

        Member member1=Member.create("1","1","1");
        Member member2=Member.create("2","1","1");
        Member member3=Member.create("3","1","1");
        Member member4=Member.create("4","1","1");
        Member member5=Member.create("5","1","1");

        Timetable timetable1=Timetable.create(member1,"1");
        Timetable timetable2=Timetable.create(member1,"2");
        Timetable timetable3=Timetable.create(member1,"3");
        Timetable timetable4=Timetable.create(member1,"4");
        Timetable timetable5=Timetable.create(member1,"5");

        groupRepository.save(group1);
        groupRepository.save(group2);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        timetableRepository.save(timetable1);
        timetableRepository.save(timetable2);
        timetableRepository.save(timetable3);
        timetableRepository.save(timetable4);
        timetableRepository.save(timetable5);

        //Schedule schedule1 = Schedule.create("1", DayOfWeek.FRI, "0900", "1000", timetable1);
        //Schedule schedule2 = Schedule.create("1", DayOfWeek.FRI, "1100", "1200", timetable1);
        //Schedule schedule3 = Schedule.create("1", DayOfWeek.FRI, "0900", "0930", timetable1);
        //Schedule schedule4 = Schedule.create("1", DayOfWeek.FRI, "1300", "1400", timetable1);


        GroupMember groupMember1 = GroupMember.create(member1, group1, Position.HOST,timetable1.getId());
        GroupMember groupMember2 = GroupMember.create(member2, group1, Position.PARTICIPANT,timetable2.getId());
        GroupMember groupMember3 = GroupMember.create(member3, group1, Position.PARTICIPANT,timetable3.getId());
        GroupMember groupMember4 = GroupMember.create(member4, group2, Position.HOST,timetable4.getId());
        GroupMember groupMember5 = GroupMember.create(member5, group2, Position.PARTICIPANT,timetable5.getId());

        groupMemberRepository.save(groupMember1);
        groupMemberRepository.save(groupMember2);
        groupMemberRepository.save(groupMember3);
        groupMemberRepository.save(groupMember4);
        groupMemberRepository.save(groupMember5);

        List<Long> timetable=groupMemberRepository.findAllTimetableIdByGroup(group1);
        List<Long> result=new ArrayList<>();
        result.add(timetable1.getId());
        result.add(timetable2.getId());
        result.add(timetable3.getId());

        Assertions.assertEquals(timetable,result);
    }
}
