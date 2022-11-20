package TimeIsGold.TimeIsGold.repository;

import TimeIsGold.TimeIsGold.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OtpRepository extends JpaRepository<Otp, Long> {

    Otp findByOtpCode(String otpCode);

    boolean existsByOtpCode(String otpCode);
}
