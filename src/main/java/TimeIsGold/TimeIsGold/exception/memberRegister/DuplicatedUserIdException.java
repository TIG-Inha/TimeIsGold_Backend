package TimeIsGold.TimeIsGold.exception.memberRegister;

public class DuplicatedUserIdException extends RuntimeException{
    public DuplicatedUserIdException() {
        super();
    }

    public DuplicatedUserIdException(String message) {
        super(message);
    }

    public DuplicatedUserIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedUserIdException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedUserIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
