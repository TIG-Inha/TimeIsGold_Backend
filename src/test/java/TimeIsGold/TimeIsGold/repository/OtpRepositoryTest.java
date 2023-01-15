//package TimeIsGold.TimeIsGold.repository;
//
//import TimeIsGold.TimeIsGold.domain.member.Member;
//import TimeIsGold.TimeIsGold.domain.member.Otp;
//import TimeIsGold.TimeIsGold.domain.OtpStatus;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class OtpRepositoryTest {
//
//    @Autowired
//    private OtpRepository otpRepository;
//
//    @Test
//    public void otpRepositoryTest() throws Exception{
//        //given
//        Otp otp = Otp.createOtp();
////        otp.setOtpStatus(OtpStatus.AVAILABLE);
//        otp.setOtpCode("1234");
//        otp.setMember(Member.createMember("abc", "123", "member0"));
//
//        //when
//        Otp saveOtp = otpRepository.save(otp);
//        //then
//        Assertions.assertThat(saveOtp.getId()).isEqualTo(otp.getId());
//    }
//
//}