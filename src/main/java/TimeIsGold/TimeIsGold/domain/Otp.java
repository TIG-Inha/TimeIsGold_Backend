package TimeIsGold.TimeIsGold.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Otp {

    @Id @GeneratedValue
    @Column(name = "otp_id")
    private Long id;

    @Column(unique = true)
    private String otpCode;

    @OneToOne(mappedBy = "otp", cascade = CascadeType.ALL)
    private Member member;

//    @Enumerated(EnumType.STRING)
//    private OtpStatus otpStatus;

    public void setMember(Member member) {
        this.member = member;
        member.setOtp(this);
    }

    public static Otp createOtp() {
        Otp otp = new Otp();
        return otp;
    }
}
