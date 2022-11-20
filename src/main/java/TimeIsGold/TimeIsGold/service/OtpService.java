package TimeIsGold.TimeIsGold.service;

import TimeIsGold.TimeIsGold.domain.Member;
import TimeIsGold.TimeIsGold.domain.Otp;
import TimeIsGold.TimeIsGold.domain.OtpStatus;
import TimeIsGold.TimeIsGold.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class OtpService {

    private final OtpRepository otpRepository;

    public Otp createOtp() {

        String generatedOtpCode = createRandString();
        while(otpRepository.existsByOtpCode(generatedOtpCode)){
            generatedOtpCode = createRandString();
        }
        Otp otp = Otp.createOtp();
        otp.setOtpCode(generatedOtpCode);
//        otp.(OtpStatus.AVAILABLE);

        return otp;
    }

    public void deleteOtp(Long otpId) {
        otpRepository.deleteById(otpId);
    }

    private String createRandString() {
        int leftLimit = 97; //a
        int rightLimit = 122; //z
        int length = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
