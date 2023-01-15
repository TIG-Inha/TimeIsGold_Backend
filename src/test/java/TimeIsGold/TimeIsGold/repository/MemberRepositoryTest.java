//package TimeIsGold.TimeIsGold.repository;
//
//import TimeIsGold.TimeIsGold.domain.member.Member;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.NoSuchElementException;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class MemberRepositoryTest {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//
//
//    @Test
//    public void save() throws Exception{
//        //given
//        Member member = Member.createMember("userId0", "1234", "user0");
//
//        //when
//        memberRepository.save(member);
//        Long memberId = member.getId();
//
//        //then
//        Member findMember = memberRepository.findById(memberId).get();
//        assertThat(memberId).isEqualTo(findMember.getId());
//
//    }
//
//    @Test
//    @DisplayName("유저ID 사용중 조회 성공")
//    public void findByUserIdTrueTest() throws Exception{
//        //given
//        Member member = Member.createMember("userId0", "1234", "user0");
//
//        //when
//        memberRepository.save(member);
//
//        boolean isExist = memberRepository.existsByUserId("userId0");
//        //then
//        assertThat(isExist).isTrue();
//    }
//
//    @Test
//    @DisplayName("유저ID 사용하는 사람 없는 경우")
//    public void findByUserIdFalseTest() throws Exception{
//        //given
//        Member member = Member.createMember("userId0", "1234", "user0");
//
//        //when
//        memberRepository.save(member);
//
//        boolean isExist = memberRepository.existsByUserId("userId1");
//        //then
//        assertThat(isExist).isFalse();
//    }
//
//    @Test
//    @DisplayName("UserId, PW로 회원 조회 성공")
//    public void findMemberByIdAndPwTest() throws Exception{
//        //given
//        Member member = Member.createMember("userId0", "1234", "user0");
//        memberRepository.save(member);
//
//        //when
//        Member findMember =
//                memberRepository.findMemberByUserIdAndPw("userId0", "1234").get();
//
//        //then
//        assertThat(member.getId()).isEqualTo(findMember.getId());
//    }
//
//    @Test
//    @DisplayName("UserId, PW로 회원 조회 실패")
//    public void findMemberByIdAndPwFailTest() throws Exception{
//        //given
//        Member member = Member.createMember("userId0", "1234", "user0");
//        memberRepository.save(member);
//
//        //when
//        assertThrows(NoSuchElementException.class, () ->{
//            memberRepository.findMemberByUserIdAndPw("userId0","1235").get();
//        });
//
//        //then
//
//    }
//}