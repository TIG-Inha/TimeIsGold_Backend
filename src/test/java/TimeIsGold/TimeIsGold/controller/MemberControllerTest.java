package TimeIsGold.TimeIsGold.controller;

import TimeIsGold.TimeIsGold.domain.Member;
import TimeIsGold.TimeIsGold.repository.MemberRepository;
import TimeIsGold.TimeIsGold.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

@SpringBootTest
public class MemberControllerTest {

    private MemberService memberService;
    private MemberRepository memberRepository;

    @Test
    public void MemberControllerTest() throws Exception{
        //given
        Member member0 = Member.createMember("id0", "1234", "user0");
        Member member1 = Member.createMember("id1", "1234", "user1");
        memberService.join(member0);
        memberService.join(member1);

        //when
        Member findMember = memberRepository.findMemberByUserId("id0").get();
        System.out.println("findMember = " + findMember.getId());

        //then
        //Assertions.assertThat(findMember).isEqualTo(member0);
    }
}
