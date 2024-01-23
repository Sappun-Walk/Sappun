package sparta.com.sappun.domain.likeBoard.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeBoardSaveRes {
    private Boolean isLiked;

    @Builder
    private LikeBoardSaveRes(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}
