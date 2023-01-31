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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "timetable")
    private List<Schedule> schedules = new ArrayList<>();

    public static Timetable create(Member member, String name) {

        Timetable timetable = new Timetable();
        timetable.setName(name);

        member.getTimetables().add(timetable);

        return timetable;
    }

    private void setName(String name) {
        this.name = name;
    }
}
