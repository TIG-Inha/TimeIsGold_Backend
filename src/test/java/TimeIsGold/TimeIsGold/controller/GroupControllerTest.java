package TimeIsGold.TimeIsGold.controller;

import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.service.login.LoginService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
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

    @Mock
    private final LoginService loginService;
    protected MockHttpServletRequest request;
    protected MockHttpSession session;

    @Autowired
    public GroupControllerTest(LoginService loginService) {
        this.loginService = loginService;
    }

    @BeforeEach
    public void setUp(){
        Member member=loginService.login("id0","1234");

        session = new MockHttpSession();

        session.setAttribute(SessionConstants.LOGIN_MEMBER, member);

        Group group=Group.create("aaa", 1L);
        session.setAttribute(SessionConstants.GROUP, group);
        session.setMaxInactiveInterval(600);

        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }


    @Test
    //@NullSource
    @DisplayName("SSE에 연결을 진행한다.")
    public void subscribe() throws Exception{
        //Member member=loginService.login("id0","1234");
        //MockHttpSession session = new MockHttpSession();
        //session.setAttribute(SessionConstants.LOGIN_MEMBER, member);

        mockMvc.perform(get("/group/{groupName}","aaa"))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Otp를 받는다.")
    public void otp() throws Exception{

        mockMvc.perform(get("/group/otp"))
                .andExpect(status().isOk());
    }

    @AfterEach
    public void clear(){
        session.clearAttributes();
        session = null;
    }
}
