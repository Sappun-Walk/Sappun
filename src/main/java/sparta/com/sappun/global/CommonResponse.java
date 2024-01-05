package sparta.com.sappun.global;

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
    private T data;

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder()
            .status(ResultCode.SUCCESS.getStatus())
            .code(ResultCode.SUCCESS.getCode())
            .message(ResultCode.SUCCESS.getMessage())
            .data(data)
            .build();
    }

    public static <T> CommonResponse<T> error(ResultCode resultCode) {
        return CommonResponse.<T>builder()
            .status(resultCode.getStatus())
            .code(resultCode.getCode())
            .message(resultCode.getMessage())
            .build();
    }
}
