package TimeIsGold.TimeIsGold.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    private String scheduleName;

    private String startTime;

    private String endTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;

    public void setMember(Member member) {
        this.member = member;
        member.getSchedules().add(this);
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
        timetable.getSchedules().add(this);
    }

    public static Schedule createSchedule(Member member, String name, String startTime, String endTime){
        Schedule schedule = new Schedule();

        schedule.setMember(member);
        schedule.setScheduleName(name);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setTimetable(member.getTimetable());

        return schedule;
    }

}
