package TimeIsGold.TimeIsGold.domain.group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByIdAndName(Long id, String name);

    //Group findByOtp(String otp);
}
