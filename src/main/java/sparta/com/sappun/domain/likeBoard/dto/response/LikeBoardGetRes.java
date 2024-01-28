package sparta.com.sappun.domain.likeBoard.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.board.dto.response.BoardToLikeGetRes;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeBoardGetRes {
    private Long id;
    private String nickname;
    private BoardToLikeGetRes board;

    @Builder
    private LikeBoardGetRes(Long id, BoardToLikeGetRes board, String nickname) {
        this.id = id;
        this.board = board;
        this.nickname = nickname;
    }
}
