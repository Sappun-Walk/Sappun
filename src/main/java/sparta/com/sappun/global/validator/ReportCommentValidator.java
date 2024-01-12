package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.DUPLICATED_REPORT_COMMENT;

import sparta.com.sappun.global.exception.GlobalException;

public class ReportCommentValidator {
    public static void checkReport(boolean isDuplicated) {
        if (isDuplicated) {
            throw new GlobalException(DUPLICATED_REPORT_COMMENT);
        }
    }
}
