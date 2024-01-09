package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.INVALID_ACCESS_TOKEN;
import static sparta.com.sappun.global.response.ResultCode.INVALID_REFRESH_TOKEN;
import static sparta.com.sappun.global.response.ResultCode.LOGIN_REQUIRED;

import sparta.com.sappun.global.exception.GlobalException;

public class TokenValidator {

    public static void checkValidAccessToken(boolean isTokenValid) {
        if (!isTokenValid) {
            throw new GlobalException(INVALID_ACCESS_TOKEN);
        }
    }

    public static void checkValidRefreshToken(boolean isTokenValid) {
        if (!isTokenValid) {
            throw new GlobalException(INVALID_REFRESH_TOKEN);
        }
    }

    public static void checkLoginRequired(boolean isLoginRequired) {
        if (isLoginRequired) {
            throw new GlobalException(LOGIN_REQUIRED);
        }
    }
}
