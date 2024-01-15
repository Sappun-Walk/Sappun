package sparta.com.sappun.domain.comment.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentToReportListGetRes {
    private List<CommentToReportListGetRes> comments;

    @Builder
    private CommentToReportListGetRes(List<CommentToReportListGetRes> comments) {
        this.comments = comments;
    }
}
