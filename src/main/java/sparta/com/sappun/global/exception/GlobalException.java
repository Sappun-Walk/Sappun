package sparta.com.sappun.global.exception;

import lombok.Getter;
import sparta.com.sappun.global.response.ResultCode;

@Getter
public class GlobalException extends RuntimeException {

    private final ResultCode resultCode;

    public GlobalException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
