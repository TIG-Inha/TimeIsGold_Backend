package TimeIsGold.TimeIsGold;

import TimeIsGold.TimeIsGold.controller.memberRegisterDto.LoginRequestDto;
import TimeIsGold.TimeIsGold.domain.Member;
import TimeIsGold.TimeIsGold.domain.Timetable;
import TimeIsGold.TimeIsGold.repository.MemberRepository;
import TimeIsGold.TimeIsGold.service.MemberService;
import org.aspectj.lang.annotation.RequiredTypes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class TimeIsGoldApplicationTests {
    /*
    @Test
    public void 로그인(){
        Member member = Member.createMember("aaa","aaa","aaa");
        Member member1 = Member.createMember("id1", "1234", "user1");

        LoginRequestDto request = new LoginRequestDto("aaa", "aaa");

        List<Member> result = MemberService.login(request);

        System.out.println(result);

    }
*/
}
