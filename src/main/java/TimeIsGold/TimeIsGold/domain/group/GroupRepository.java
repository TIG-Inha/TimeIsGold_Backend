package TimeIsGold.TimeIsGold.domain.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {



    Group findByIdAndName(Long id, String name);

    Group findByOtp(String otp);

    @Transactional
    void deleteById(Long id);
}
