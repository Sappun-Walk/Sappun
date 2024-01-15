package sparta.com.sappun.domain.reportComment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportCommentReq {

    private Long userId;
    @NotBlank private String reason;

    @Builder
    private ReportCommentReq(String reason) {
        this.reason = reason;
    }
}
