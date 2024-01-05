package sparta.com.sappun.global.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparta.com.sappun.global.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public CommonResponse<Void> handleException(GlobalException e) {
        return CommonResponse.error(e.getResultCode());
    }
}
