package sparta.com.sappun.domain.comment.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveReq {
    private Long boardId;
    private Long userId;

    @Size(max = 500, message = "500자 이내로 입력해주세요.") // TODO: 255글자 이상으로 작성안됨 확인필요 403Error 발생
    private String content;

    private Integer likeCount;
    private Integer reportCount;

    @Builder
    private CommentSaveReq(String content, Integer likeCount, Integer reportCount) {
        this.content = content;
        this.likeCount = likeCount;
        this.reportCount = reportCount;
    }
}
