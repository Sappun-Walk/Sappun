package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.INVALID_TOKEN;
import static sparta.com.sappun.global.response.ResultCode.LOGIN_REQUIRED;

import sparta.com.sappun.global.exception.GlobalException;

public class TokenValidator {

    public static void checkValidToken(boolean isTokenValid) {
        if (!isTokenValid) {
            throw new GlobalException(INVALID_TOKEN);
        }
    }

    public static void checkLoginRequired(boolean isLoginRequired) {
        if (isLoginRequired) {
            throw new GlobalException(LOGIN_REQUIRED);
        }
    }
}
