package TimeIsGold.TimeIsGold.domain.groupMember;

import TimeIsGold.TimeIsGold.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    void deleteByGroup(Group group);
}
