package TimeIsGold.TimeIsGold.exception;

public class DuplicatedUserId extends RuntimeException{
    public DuplicatedUserId() {
        super();
    }

    public DuplicatedUserId(String message) {
        super(message);
    }

    public DuplicatedUserId(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedUserId(Throwable cause) {
        super(cause);
    }

    protected DuplicatedUserId(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
