package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.DUPLICATED_REPORT_BOARD;

import sparta.com.sappun.global.exception.GlobalException;

public class ReportBoardValidator {
    public static void checkReport(boolean isDuplicated) {
        if (isDuplicated) {
            throw new GlobalException(DUPLICATED_REPORT_BOARD);
        }
    }
}
