package TimeIsGold.TimeIsGold.domain.groupMember;

import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    @Transactional
    void deleteByGroup(Group group);

    List<GroupMember> findAllByGroup(Group group);

    @Query("select a.timetable_id from GroupMember a where a.group = :group")
    List<Long> findAllTimetableIdByGroup(Group group);


    GroupMember findByGroupAndMember(Group group, Member member);
}
