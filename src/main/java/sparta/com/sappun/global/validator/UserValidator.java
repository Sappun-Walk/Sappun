package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.*;

import java.util.Objects;
import java.util.regex.Pattern;
import sparta.com.sappun.domain.user.dto.request.UserProfileUpdateReq;
import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.exception.GlobalException;

public class UserValidator {
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]{4,15}$";
    private static final String NICKNAME_PATTERN = "^[가-힣a-zA-Z0-9]{2,10}$";

    public static void validate(UserSignupReq req) {
        if (!checkConfirmPassword(req.getPassword(), req.getConfirmPassword())) {
            throw new GlobalException(NOT_MATCHED_PASSWORD);
        }
    }

    public static void validate(UserProfileUpdateReq req) {
        if (!Objects.equals(req.getUsername(), "")
                && !Pattern.matches(USERNAME_PATTERN, req.getUsername())) {
            throw new GlobalException(BAD_REQUEST);
        }
        if (!Objects.equals(req.getNickname(), "")
                && !Pattern.matches(NICKNAME_PATTERN, req.getNickname())) {
            throw new GlobalException(BAD_REQUEST);
        }
    }

    public static void validate(User user) {
        if (checkIsNull(user)) {
            throw new GlobalException(NOT_FOUND_USER);
        }
    }

    public static void checkPassword(boolean isMatched) {
        if (!isMatched) {
            throw new GlobalException(NOT_MATCHED_PASSWORD);
        }
    }

    public static void checkEmail(boolean isDuplicated) {
        if (isDuplicated) {
            throw new GlobalException(DUPLICATED_EMAIL);
        }
    }

    private static boolean checkConfirmPassword(String password1, String password2) {
        return Objects.equals(password1, password2);
    }

    private static boolean checkIsNull(User user) {
        return user == null;
    }

    public static void checkDuplicatedUsername(boolean isDuplicated) {
        if (isDuplicated) {
            throw new GlobalException(DUPLICATED_USERNAME);
        }
    }

    public static void checkDuplicatedNickname(boolean isDuplicated) {
        if (isDuplicated) {
            throw new GlobalException(DUPLICATED_NICKNAME);
        }
    }

    public static void checkEqualsPassword(boolean isMatched) {
        if (!isMatched) {
            throw new GlobalException(DIFFERENT_PASSWORD);
        }
    }
}
