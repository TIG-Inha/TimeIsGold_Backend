package TimeIsGold.TimeIsGold.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    boolean existsByUserId(String userId);


    Optional<Member> findMemberByUserId(String userId);
    
    Member findByUserIdAndPw(String userId, String pw);


    Optional<Member> findMemberByUserIdAndPw(String userId, String pw);
}
