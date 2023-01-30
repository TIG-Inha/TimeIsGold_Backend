package TimeIsGold.TimeIsGold.domain.schedule;

import TimeIsGold.TimeIsGold.domain.member.Member;
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

    private Integer time;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;


    public void setMember(Member member) {
        this.member = member;
        member.getSchedules().add(this);
    }


    public static Schedule createSchedule(Member member, String name, Integer time){
        Schedule schedule = new Schedule();

        schedule.setMember(member);
        schedule.setScheduleName(name);
        schedule.setTime(time);

        return schedule;
    }

}
