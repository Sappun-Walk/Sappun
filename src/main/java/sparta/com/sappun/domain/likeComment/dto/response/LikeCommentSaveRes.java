package sparta.com.sappun.domain.likeComment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeCommentSaveRes {
    private Boolean isLiked;

    @Builder
    private LikeCommentSaveRes(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}
