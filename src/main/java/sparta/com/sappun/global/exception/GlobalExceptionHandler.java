package sparta.com.sappun.global.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparta.com.sappun.global.response.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public CommonResponse<Void> handleException(GlobalException e) {
        return CommonResponse.error(e.getResultCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<Void> processValidationError(
            MethodArgumentNotValidException e) { // Validation 예외를 잡아줌
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }

        return CommonResponse.error(builder.toString());
    }
}
