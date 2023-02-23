package TimeIsGold.TimeIsGold.domain.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {



    Group findByIdAndName(Long id, String name);

    Group findByOtp(String otp);

    @Transactional //transactional 메소드 위에 놓기, 나중에 수정 필요
    void deleteById(Long id);
}
