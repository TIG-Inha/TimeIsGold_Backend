package TimeIsGold.TimeIsGold.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DuplicatedUserId.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response DuplicatedUserId(Exception e) {

        return new Response("409", "해당 id는 사용중입니다.");
    }



    //Response DTO
    @Data
    @AllArgsConstructor
    static class Response{
        private String code;
        private String message;
    }
}
