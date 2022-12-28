package TimeIsGold.TimeIsGold.service;

import TimeIsGold.TimeIsGold.controller.memberRegisterDto.LoginRequestDto;
import TimeIsGold.TimeIsGold.domain.Member;
import TimeIsGold.TimeIsGold.domain.Timetable;
import TimeIsGold.TimeIsGold.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = Member.createMember("abc00", "1234", "user0");

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

    @Test
    public void 로그인(){
        Member member = Member.createMember("aaa","aaa","aaa");
        Member member1 = Member.createMember("id1", "1234", "user1");
        memberService.join(member);

        LoginRequestDto request = new LoginRequestDto("aaa", "aaa");

        Optional<Member> result = memberService.login(request.getId(), request.getPw());

        System.out.println(result);

    }

}