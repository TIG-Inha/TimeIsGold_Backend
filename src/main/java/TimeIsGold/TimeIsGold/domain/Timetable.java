package TimeIsGold.TimeIsGold.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Timetable {

    @Id @GeneratedValue
    @Column(name = "timetable_id")
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL) //cascade프로퍼티가 필요한가?
    private List<Schedule> schedules;

    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String sun;

    public static Timetable createTimetable() {
        return new Timetable();
    }
}
