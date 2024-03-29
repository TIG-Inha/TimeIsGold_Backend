package TimeIsGold.TimeIsGold.domain.timetable;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class TimetableForm {

    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String sun;
}
