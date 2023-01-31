package TimeIsGold.TimeIsGold.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    boolean existsByUserId(String userId);


    Optional<Member> findMemberByUserId(String userId);
    
   
    @Query("select m from Member m left join fetch m.timetables t where m.userId = :id and m.pw = :pw")
    Member findByUserIdAndPw(String userId, String pw);


    Optional<Member> findMemberByUserIdAndPw(String userId, String pw);
}
