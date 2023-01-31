//package TimeIsGold.TimeIsGold.service;
//
//import TimeIsGold.TimeIsGold.controller.memberRegisterDto.LoginRequestDto;
//import TimeIsGold.TimeIsGold.domain.Member;
//import TimeIsGold.TimeIsGold.domain.Timetable;
//import TimeIsGold.TimeIsGold.repository.MemberRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@Transactional
//@ExtendWith(MockitoExtension.class)
//class MemberServiceImplTest {
//
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired
//    private MockMvc mvc;//웹 API를 테스트할 때 사용
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    @InjectMocks
//    private MemberService memberService;
//
//    @Mock
//    private MemberRepository memberRepository;
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
//    public void 로그인() throws Exception {
//        Member member = Member.createMember("aaa","aaa","aaa");
//        Member member1 = Member.createMember("id1", "1234", "user1");
//        memberService.join(member);
//
//        LoginRequestDto request = new LoginRequestDto("aaa", "aaa");
//
//        //Optional<Member> result = memberService.login(request.getId(), request.getPw());
//
//        String content = objectMapper.writeValueAsString(request);
//
//        mvc.perform(post("/login")
//                        .content(content)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    public void 로그아웃() throws Exception{
//
//        //쿠키는 어떻게 테스트해야 하지?
//        mvc.perform(get("/logout"))
//                .andExpect(status().isOk());
//    }
//
//}