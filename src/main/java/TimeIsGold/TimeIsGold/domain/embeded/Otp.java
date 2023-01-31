package TimeIsGold.TimeIsGold.domain.embeded;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Otp {

    @Column(unique = true)
    private String otpCode;

    public static Otp createOtp(String otpCode) {
        Otp otp = new Otp();
        otp.otpCode = otpCode;
        return otp;
    }
}
