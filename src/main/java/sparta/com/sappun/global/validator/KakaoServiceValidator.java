package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.DUPLICATED_EMAIL;

import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.exception.GlobalException;

public class KakaoServiceValidator {
    public static void checkEmail(User user) {
        if (!checkIsNull(user)) {
            throw new GlobalException(DUPLICATED_EMAIL);
        }
    }

    private static boolean checkIsNull(User user) {
        return user == null;
    }
}
