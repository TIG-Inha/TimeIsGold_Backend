package TimeIsGold.TimeIsGold.domain.timetable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Timetable {

    @Id @GeneratedValue
    @Column(name = "timetable_id")
    private Long id;

    @Embedded
    private TimetableForm timetableForm;

    public static Timetable createTimetable(TimetableForm timetableForm) {
        Timetable timetable = new Timetable();
        timetable.timetableForm = timetableForm;

        return timetable;
    }
}
