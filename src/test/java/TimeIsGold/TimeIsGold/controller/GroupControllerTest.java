package TimeIsGold.TimeIsGold.controller;

import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.service.login.LoginService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class GroupControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Mock
    private final LoginService loginService;
    protected MockHttpServletRequest request;
    protected MockHttpSession session;
    protected Group group;


    @Autowired
    public GroupControllerTest(LoginService loginService) {
        this.loginService = loginService;
    }

    @BeforeEach
    public void setUp(){
        Member member=loginService.login("id0","1234");

        session = new MockHttpSession();

        session.setAttribute(SessionConstants.LOGIN_MEMBER, member);

        group=Group.create("aaa", 1L);
        session.setAttribute(SessionConstants.GROUP, group);
        session.setMaxInactiveInterval(600);

        //request = new MockHttpServletRequest();
        //request.setSession(session);
        //RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }


    @Test
    //@NullSource
    @DisplayName("SSE에 연결을 진행한다.")
    public void subscribe() throws Exception{
        //Member member=loginService.login("id0","1234");
        //MockHttpSession session = new MockHttpSession();
        //session.setAttribute(SessionConstants.LOGIN_MEMBER, member);


        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockMvc.perform(get("/group/{groupName}","aaa").session(session))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Otp를 받는다.")
    public void otp() throws Exception{

        mockMvc.perform(get("/group/otp"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("참여할 경우")
    public void participate() throws Exception{
        String otp = group.getOtp();
        Long num= group.getNum();

        mockMvc.perform(get("/group/participate/{otp}", otp))
                .andExpect(status().isOk());

        //참여자 수가 +1 됐는지
        Assertions.assertEquals(num+1, group.getNum());

        //evencache의 size가 그룹 수만큼 늘어났는지
    }



    @AfterEach
    public void clear(){
        session.clearAttributes();
        session = null;
    }


}
