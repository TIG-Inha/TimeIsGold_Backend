package TimeIsGold.TimeIsGold.exception.timetable;

public class DuplicatedNameException extends RuntimeException{
    public DuplicatedNameException() {
        super();
    }

    public DuplicatedNameException(String message) {
        super(message);
    }

    public DuplicatedNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedNameException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
