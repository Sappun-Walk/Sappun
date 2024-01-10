package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.NOT_FOUND_BOARD;

import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.global.exception.GlobalException;

public class BoardValidator {
    public static void validate(Board board) {
        if (isNullBoard(board)) {
            throw new GlobalException(NOT_FOUND_BOARD);
        }
    }

    private static boolean isNullBoard(Board board) {
        return board == null;
    }
}
