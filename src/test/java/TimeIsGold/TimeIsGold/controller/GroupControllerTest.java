package TimeIsGold.TimeIsGold.controller;

import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.domain.group.EmitterRepository;
import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.group.GroupRepository;
import TimeIsGold.TimeIsGold.domain.groupMember.GroupMemberRepository;
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
import org.hamcrest.core.Is;

import javax.transaction.Transactional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class GroupControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    private EmitterRepository emitterRepository;
    @Autowired
    private LoginService loginService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    protected MockHttpServletRequest request;
    protected MockHttpSession session;
    protected Group group;



    @BeforeEach
    public void setUp(){
        Member member=loginService.login("id0","1234");

        session = new MockHttpSession();

        session.setAttribute(SessionConstants.LOGIN_MEMBER, member);

        group=Group.create("aaa", 1L);
        groupRepository.save(group);

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

        Assertions.assertEquals(groupRepository.count(),2); //before에 이미 저장되어 있는 group이 있기 때문에 2
        Assertions.assertEquals(groupMemberRepository.count(),1);
        Assertions.assertEquals(emitterRepository.countEmitter(),1);
        Assertions.assertEquals(emitterRepository.countEventCache(),1);
    }

    @Test
    @DisplayName("Otp를 받는다.")
    public void otp() throws Exception{
        //group의 otp가 바뀌었는지 확인한다.
        Group temp = (Group) session.getAttribute(SessionConstants.GROUP);
        Group group1 = groupRepository.findByIdAndName(temp.getId(), temp.getName());

        mockMvc.perform(get("/group/otp").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.otp").value(group1.getOtp()));
    }

    @Test
    @DisplayName("참여할 경우")
    public void participate() throws Exception{
        String otp = group.getOtp();
        Long num= group.getNum();

        //그룹 생성
        mockMvc.perform(get("/group/{groupName}",group.getName()).session(session))
                .andExpect(status().isOk());

        Assertions.assertEquals(groupRepository.count(),2); //before에 이미 저장되어 있는 group이 있기 때문에 2
        Assertions.assertEquals(groupMemberRepository.count(),1);
        Assertions.assertEquals(emitterRepository.countEmitter(),1);
        Assertions.assertEquals(emitterRepository.countEventCache(), 1);

        //그룹 참여
        mockMvc.perform(get("/group/participate/{otp}", otp).session(session))
                .andExpect(status().isOk());

        //참여자 수가 +1 됐는지
        Assertions.assertEquals(num+1, group.getNum());

        Assertions.assertEquals(groupRepository.count(),2); //before에 이미 저장되어 있는 group이 있기 때문에 2
        Assertions.assertEquals(groupMemberRepository.count(),2);

        //emitter와 even cache의 size가 그룹원 수만큼 늘어났는지
        Assertions.assertEquals(emitterRepository.countEmitter(), 2);
        Assertions.assertEquals(emitterRepository.countEventCache(), 2);

    }

    // Group 취소 api 테스트
    // 그룹 관련된게 다 사라져는지 체크, 그룹 멤버, 그룹, emitter, cache 등




    @AfterEach
    public void clear(){
        session.clearAttributes();
        session = null;
    }


}
