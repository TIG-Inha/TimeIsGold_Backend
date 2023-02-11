package TimeIsGold.TimeIsGold.domain.group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {


    //Group findByOtp(String otp);
}
