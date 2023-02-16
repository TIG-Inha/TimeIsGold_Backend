package TimeIsGold.TimeIsGold.repository;

import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.group.GroupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    @DisplayName("findByOtp가 잘 작동되는지 확인한다.")
    public void findByOtp(){
        Group group1 = Group.create("a",1L);
        Group group2 = Group.create("b",1L);
        groupRepository.save(group1);
        groupRepository.save(group2);

        Assertions.assertEquals(groupRepository.findByOtp(group1.getOtp()),group1);
    }

    @Test
    @DisplayName("deleteById가 잘 작동되는지 확인한다.")
    public void deleteById(){
        Group group1 = Group.create("a",1L);
        Group group2 = Group.create("b",1L);
        Group group3 = Group.create("c",1L);

        groupRepository.save(group1);
        groupRepository.save(group2);
        groupRepository.save(group3);

        groupRepository.deleteById(group1.getId());

        Assertions.assertEquals(groupRepository.count(), 2);
    }
}
