package TimeIsGold.TimeIsGold.exception;

import TimeIsGold.TimeIsGold.exception.login.LoginException;
import TimeIsGold.TimeIsGold.exception.member.MemberRegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 회원가입시 EX-handler
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MemberRegisterException.class)
    public ResponseEntity<ErrorResult> memberRegisterExHandle(MemberRegisterException e) {

        ErrorResult errorResult = new ErrorResult("REGISTER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResult> loginExHandle(LoginException e) {

        ErrorResult errorResult = new ErrorResult("LOGIN-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
}