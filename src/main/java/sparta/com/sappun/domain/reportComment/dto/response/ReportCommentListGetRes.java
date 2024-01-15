package sparta.com.sappun.domain.reportComment.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportCommentListGetRes {
    private List<ReportCommentGetRes> reportComments;

    @Builder
    private ReportCommentListGetRes(List<ReportCommentGetRes> reportComments) {
        this.reportComments = reportComments;
    }
}
