package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.ACCESS_DENIED;
import static sparta.com.sappun.global.response.ResultCode.NOT_FOUND_COMMENT;

import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.exception.GlobalException;

public class CommentValidator {

    public static void validate(Comment comment) {
        if (isNullComment(comment)) {
            throw new GlobalException(NOT_FOUND_COMMENT);
        }
    }

    public static void checkCommentUser(Long id, Long userId) { // 접근자와 작성자가 동일한지 확인
        if (!id.equals(userId)) {
            throw new GlobalException(ACCESS_DENIED);
        }
    }

    public static void checkCommentUser(User accessor, User author) {
        if (!accessor.getId().equals(author.getId())
                && accessor.getRole() != Role.ADMIN) { // 접근자와 작성자가 동일하거나 접근자가 관리자인지 확인
            throw new GlobalException(ACCESS_DENIED);
        }
    }

    private static boolean isNullComment(Comment comment) {
        return comment == null;
    }
}
