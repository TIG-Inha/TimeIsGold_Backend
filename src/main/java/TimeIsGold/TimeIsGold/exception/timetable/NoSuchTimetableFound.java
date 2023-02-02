package TimeIsGold.TimeIsGold.exception.timetable;

public class NoSuchTimetableFound extends RuntimeException{
    public NoSuchTimetableFound() {
        super();
    }

    public NoSuchTimetableFound(String message) {
        super(message);
    }

    public NoSuchTimetableFound(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchTimetableFound(Throwable cause) {
        super(cause);
    }

    protected NoSuchTimetableFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
