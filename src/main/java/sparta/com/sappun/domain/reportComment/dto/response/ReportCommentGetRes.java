package sparta.com.sappun.domain.reportComment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportCommentGetRes {
    private Long id;
    private Long boardId;
    private String nickname;
    private String reason;
    private CommentToReportGetRes comment;

    @Builder
    private ReportCommentGetRes(
            Long id, Long boardId, String reason, CommentToReportGetRes comment, String nickname) {
        this.id = id;
        this.boardId = boardId;
        this.reason = reason;
        this.comment = comment;
        this.nickname = nickname;
    }
}
