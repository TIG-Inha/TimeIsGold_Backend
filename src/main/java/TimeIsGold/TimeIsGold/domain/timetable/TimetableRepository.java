package TimeIsGold.TimeIsGold.domain.timetable;

import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    Boolean existsByMemberAndName(Member member, String name);

    Boolean existsByMemberAndId(Member member, Long id);
}
