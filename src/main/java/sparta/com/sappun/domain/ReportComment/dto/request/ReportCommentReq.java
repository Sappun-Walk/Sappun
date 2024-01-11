package sparta.com.sappun.domain.ReportComment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportCommentReq {

    private Long userId;
    private Long reportCommentId;
    @NotBlank private String reason;

    @Builder
    private ReportCommentReq(Long reportCommentId, String reason) {
        this.reportCommentId = reportCommentId;
        this.reason = reason;
    }
}
