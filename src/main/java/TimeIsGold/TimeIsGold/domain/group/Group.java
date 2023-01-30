package TimeIsGold.TimeIsGold.domain.group;

import TimeIsGold.TimeIsGold.domain.member.Otp;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableForm;

import javax.persistence.*;

@Entity
public class Group {

    @Id @GeneratedValue
    @Column(name = "column_id")
    private Long id;

    private String name;

    @Embedded
    private Otp groupOtp;

    @Embedded
    private TimetableForm compSet;

    @Enumerated(EnumType.STRING)
    private Position position;
}
