package TimeIsGold.TimeIsGold.domain.schedule;

import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private String startTime;

    private String endTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;


    public static Schedule create(String name, DayOfWeek dayOfWeek,
                                  String startTime, String endTime,
                                          Timetable timetable){
        Schedule schedule = new Schedule();

        schedule.setName(name);
        schedule.setDayOfWeek(dayOfWeek);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setTimetable(timetable);

        timetable.getSchedules().add(schedule);

        return schedule;
    }

    private void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public void update(String name, DayOfWeek dayOfWeek,
                           String startTime, String endTime){
        this.name = name;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void setName(String scheduleName) {
        this.name = scheduleName;
    }

    private void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    private void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    private void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
