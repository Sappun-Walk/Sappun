package sparta.com.sappun.domain.reportComment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportCommentRes {

    private Long id;
    private Long reportCommentId;
    private String reason;
    private Long reporterUserId;

    @Builder
    private ReportCommentRes(Long id, Long reportCommentId, String reason, Long reporterUserId) {
        this.id = id;
        this.reporterUserId = reporterUserId;
        this.reportCommentId = reportCommentId;
        this.reason = reason;
    }
}
