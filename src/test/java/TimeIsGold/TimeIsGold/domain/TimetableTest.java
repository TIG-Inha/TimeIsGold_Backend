package TimeIsGold.TimeIsGold.domain;

import TimeIsGold.TimeIsGold.domain.embeded.TimetableForm;
import org.junit.jupiter.api.Test;

class TimetableTest {

    @Test
    public void TimetableTest() throws Exception{

        TimetableForm build = TimetableForm.builder()
                .mon("월요일")
                .tue("화요일")
                .fri("금요일")
                .build();

        Timetable timetable = Timetable.createTimetable(build);

        System.out.println("timetable = " + timetable.getTimetableForm().getMon());
        System.out.println("timetable = " + timetable.getTimetableForm().getTue());
        System.out.println("timetable = " + timetable.getTimetableForm().getWed());
        System.out.println("timetable = " + timetable.getTimetableForm().getFri());
    }

}