package TimeIsGold.TimeIsGold.domain.groupMember;

import TimeIsGold.TimeIsGold.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    @Transactional
    void deleteByGroup(Group group);

    List<GroupMember> findAllByGroup(Group group);
}
