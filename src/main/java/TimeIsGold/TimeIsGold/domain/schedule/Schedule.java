package TimeIsGold.TimeIsGold.domain.schedule;

import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
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
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;


    public static Schedule create(String name, String startTime, String endTime,
                                          Timetable timetable){
        Schedule schedule = new Schedule();

        schedule.setScheduleName(name);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);

        timetable.getSchedules().add(schedule);

        return schedule;
    }

    private void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    private void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    private void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
