package sparta.com.sappun.domain.comment.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDeleteReq {
    private Long commentId;
    private Long userId;

    @Builder
    private CommentDeleteReq(Long commentId, Long userId) {
        this.commentId = commentId;
        this.userId = userId;
    }
}
