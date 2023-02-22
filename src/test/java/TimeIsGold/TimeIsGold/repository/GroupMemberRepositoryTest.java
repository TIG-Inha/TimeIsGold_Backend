package TimeIsGold.TimeIsGold.repository;

import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.group.GroupRepository;
import TimeIsGold.TimeIsGold.domain.group.Position;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMember;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMemberRepository;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class GroupMemberRepositoryTest {

    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private MemberRepository memberRepository;

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
}
