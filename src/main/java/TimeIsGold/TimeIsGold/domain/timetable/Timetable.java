package TimeIsGold.TimeIsGold.domain.timetable;

//import TimeIsGold.TimeIsGold.domain.timetableSchedule.TimetableSchedule;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.schedule.Schedule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Timetable {

    @Id @GeneratedValue
    @Column(name = "timetable_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();

    public static Timetable create(Member member, String name) {

        Timetable timetable = new Timetable();
        timetable.setName(name);
        timetable.setMember(member);

        member.getTimetables().add(timetable);

        return timetable;
    }

    public void deleteSchedule(Long scheduleId) {
        Schedule findSchedule = schedules.stream()
                .filter(s -> s.getId().equals(scheduleId))
                .findFirst()
                .get();

        schedules.remove(findSchedule);
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setMember(Member member) {
        this.member = member;
    }
}
