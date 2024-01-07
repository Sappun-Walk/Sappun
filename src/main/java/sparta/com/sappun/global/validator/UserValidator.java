package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.ResultCode.NOT_MATCHED_PASSWORD;

import java.util.Objects;
import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.global.exception.GlobalException;

public class UserValidator {
    public static void validate(UserSignupReq req) {
        if (!checkConfirmPassword(req.getPassword(), req.getConfirmPassword())) {
            throw new GlobalException(NOT_MATCHED_PASSWORD);
        }
    }

    private static boolean checkConfirmPassword(String password1, String password2) {
        return Objects.equals(password1, password2);
    }
}
