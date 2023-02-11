package TimeIsGold.TimeIsGold.exception;

import TimeIsGold.TimeIsGold.api.ApiResponse;
import TimeIsGold.TimeIsGold.exception.group.SessionExpireException;
import TimeIsGold.TimeIsGold.exception.login.LoginException;
import TimeIsGold.TimeIsGold.exception.member.MemberRegisterException;
import TimeIsGold.TimeIsGold.exception.timetable.DuplicatedNameException;
import TimeIsGold.TimeIsGold.exception.timetable.NoSuchTimetableFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse bindingExHandle(BindingResult bindingResult) {

        ErrorResponse errorResponse = ErrorResponse.of(bindingResult);

        return ApiResponse.createError(null, errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedNameException.class)
    public ApiResponse duplicatedTableNameExHandle(DuplicatedNameException e) {

        return ApiResponse.createError(e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchTimetableFound.class)
    public ApiResponse noSuchTimetableFoundExHandle(NoSuchTimetableFound e) {

        return ApiResponse.createError(e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse runtimeExHandle(RuntimeException e) {

        return ApiResponse.createError(e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SessionExpireException.class)
    public ApiResponse sessionExpireExHandle(SessionExpireException e){
        return ApiResponse.createError(e.getMessage(), null);
    }
}
