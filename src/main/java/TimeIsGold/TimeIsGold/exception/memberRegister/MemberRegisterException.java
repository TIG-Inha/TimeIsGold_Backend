package TimeIsGold.TimeIsGold.exception.memberRegister;

public class MemberRegisterException extends RuntimeException{
    public MemberRegisterException() {
        super();
    }

    public MemberRegisterException(String message) {
        super(message);
    }

    public MemberRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberRegisterException(Throwable cause) {
        super(cause);
    }

    protected MemberRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
