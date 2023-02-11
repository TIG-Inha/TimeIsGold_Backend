package TimeIsGold.TimeIsGold.domain.otp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Otp {

    @Column(unique = true)
    private String otpCode;

    public static Otp createOtp() {
        Otp otp = new Otp();
        //otp.otpCode = otpCode;

        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for(int i=0;i<8;i++){
            int index=rnd.nextInt(3);

            switch (index){
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        otp.otpCode = key.toString();

        return otp;
    }
}
