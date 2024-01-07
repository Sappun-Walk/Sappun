package sparta.com.sappun.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {

    private HttpStatus status;
    private Integer code;
    private String message;

    @JsonInclude(Include.NON_EMPTY)
    private T data;

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder()
                .status(ResultCode.SUCCESS.getStatus())
                .code(ResultCode.SUCCESS.getStatus().value())
                .message(ResultCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> error(ResultCode resultCode) {
        return CommonResponse.<T>builder()
                .status(resultCode.getStatus())
                .code(resultCode.getStatus().value())
                .message(resultCode.getMessage())
                .build();
    }

    public static <T> CommonResponse<T> error(String message) { // Validation 을 위한 error 응답
        return CommonResponse.<T>builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build();
    }
}
