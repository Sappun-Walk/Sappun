package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.ACCESS_DENIED;
import static sparta.com.sappun.global.response.ResultCode.NOT_FOUND_BOARD;

import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.exception.GlobalException;

public class BoardValidator {
    public static void validate(Board board) {
        if (isNullBoard(board)) {
            throw new GlobalException(NOT_FOUND_BOARD);
        }
    }

    public static void checkBoardUser(Long id, Long userId) {
        if (!id.equals(userId)) {
            throw new GlobalException(ACCESS_DENIED);
        }
    }

    public static void checkBoardUser(User accessor, User author) {
        if (!accessor.getId().equals(author.getId()) && accessor.getRole() != Role.ADMIN) {
            throw new GlobalException(ACCESS_DENIED);
        }
    }

    private static boolean isNullBoard(Board board) {
        return board == null;
    }
}
