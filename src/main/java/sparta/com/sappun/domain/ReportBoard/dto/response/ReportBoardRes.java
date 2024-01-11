package sparta.com.sappun.domain.ReportBoard.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportBoardRes {
    private Long id;
    private Long reportedBoardId;
    private String reason;
    private Long reporterUserId;

    @Builder
    public ReportBoardRes(Long id, Long reportedBoardId, String reason, Long reporterUserId) {
        this.id = id;
        this.reporterUserId = reporterUserId;
        this.reportedBoardId = reportedBoardId;
        this.reason = reason;
    }
}
