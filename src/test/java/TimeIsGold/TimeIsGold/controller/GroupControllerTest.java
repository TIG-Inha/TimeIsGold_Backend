package TimeIsGold.TimeIsGold.controller;

import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.service.login.LoginService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class GroupControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    //@NullSource
    @DisplayName("SSE에 연결을 진행한다.")
    public void subscribe() throws Exception{
        //Member member=loginService.login("id0","1234");
        MockHttpSession session = new MockHttpSession();
        //session.setAttribute(SessionConstants.LOGIN_MEMBER, member);

        mockMvc.perform(get("/group/{groupName}","aaa").session(session))
                .andExpect(status().isOk());

    }
}
