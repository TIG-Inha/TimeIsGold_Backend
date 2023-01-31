package TimeIsGold.TimeIsGold.api;

import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, null, data);
    }

    public static ApiResponse<?> createSuccessWithOutContent() {
        return new ApiResponse<>(SUCCESS_STATUS, null, null);
    }

    public static ApiResponse<?> createFail(String message) {

        return new ApiResponse<>(FAIL_STATUS, message, null);
    }

    // 예외 발생으로 API 호출 실패시 반환
    public static ApiResponse<?> createError(String message) {
        return new ApiResponse<>(ERROR_STATUS,  message,null);
    }

    private ApiResponse(String status, String message,T data) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
