//package TimeIsGold.TimeIsGold.controller;
//
//import TimeIsGold.TimeIsGold.api.member.MemberController;
//import TimeIsGold.TimeIsGold.domain.member.Member;
//import MemberRepository;
//import TimeIsGold.TimeIsGold.service.member.MemberService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class MemberControllerTest {
//
//    @Mock
//    private MemberService memberService;
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @InjectMocks
//    private MemberController memberController;
//
//    private MockMvc mockMvc;
//    @BeforeEach
//    private void init() {
//        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
//    }
//
//    @Test
//    @DisplayName("ID중복체크api테스트")
//    public void checkDuplicatedUserIdTest() throws Exception{
//        //given
//        Member member = Member.createMember("id0", "1234", "user0");
//
//        DuplicatedUserIdApiRequest request = new DuplicatedUserIdApiRequest();
//        request.setUserId("id0");
//
//        doReturn(true).when(memberRepository.existsByUserId(member.getUserId()));
//
////        //when
////        mockMvc.perform(
////                MockMvcRequestBuilders.get("/register?userId=id0")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(new Gson().)
////        );
////
////        //then
////        Assertions.assertThat(findMemberOptional.get()).isEqualTo(member);
//    }
//
//    static class DuplicatedUserIdApiRequest{
//        private String userId;
//
//        public String getUserId() {
//            return userId;
//        }
//
//        public void setUserId(String userId) {
//            this.userId = userId;
//        }
//    }
//}
