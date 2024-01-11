package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.ACCESS_DENY;
import static sparta.com.sappun.global.response.ResultCode.NOT_FOUND_COMMENT;

import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.global.exception.GlobalException;

public class CommentValidator {

    public static void validate(Comment comment) {
        if (isNullComment(comment)) {
            throw new GlobalException(NOT_FOUND_COMMENT);
        }
    }

    public static void checkCommentUser(Long id, Long userId) {
        if (!id.equals(userId)) {
            throw new GlobalException(ACCESS_DENY);
        }
    }

    private static boolean isNullComment(Comment comment) {
        return comment == null;
    }
}
