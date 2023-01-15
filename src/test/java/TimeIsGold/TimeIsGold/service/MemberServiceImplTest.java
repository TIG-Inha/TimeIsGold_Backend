//package TimeIsGold.TimeIsGold.service;
//
//import TimeIsGold.TimeIsGold.api.login.dto.LoginRequestDto;
//import TimeIsGold.TimeIsGold.domain.member.Member;
//import MemberRepository;
//import TimeIsGold.TimeIsGold.service.member.MemberService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//class MemberServiceImplTest {
//
//
//    @InjectMocks
//    private MemberService memberService;
//
//
//
//    @Test
//    public void 회원가입() throws Exception{
//        //given
//        Member member = Member.createMember("abc00", "1234", "user0");
//
//        //when
//        Long joinMember = memberService.join(member);
//
//        //then
//        Assertions.assertThat(joinMember).isEqualTo(member.getId());
//    }
//    @Test
//    public void OTP로_회원조회() throws Exception{
//        //given
//
//        Member member = Member.createMember("abc", "123", "user1");
//        memberService.join(member);
//
//
//        //when
//
//        //then
//    }
//
//    @Test
//    public void 로그인(){
//        Member member = Member.createMember("aaa","aaa","aaa");
//        Member member1 = Member.createMember("id1", "1234", "user1");
//        memberService.join(member);
//
//        LoginRequestDto request = new LoginRequestDto("aaa", "aaa");
//
//        Optional<Member> result = memberService.login(request.getId(), request.getPw());
//
//        System.out.println(result);
//
//    }
//
//}