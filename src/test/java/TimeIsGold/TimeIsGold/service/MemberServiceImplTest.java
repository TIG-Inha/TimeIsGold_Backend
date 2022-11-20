package TimeIsGold.TimeIsGold.service;

import TimeIsGold.TimeIsGold.domain.Member;
import TimeIsGold.TimeIsGold.domain.Timetable;
import TimeIsGold.TimeIsGold.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private OtpService otpService;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = Member.createMember("abc00", "1234", "user0");
        member.setOtp(otpService.createOtp());

        //when
        Long joinMember = memberService.join(member);

        //then
        Assertions.assertThat(joinMember).isEqualTo(member.getId());
    }
    @Test
    public void OTP로_회원조회() throws Exception{
        //given

        Member member = Member.createMember("abc", "123", "user1");
        memberService.join(member);


        //when

        //then
    }


}