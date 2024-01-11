package sparta.com.sappun.domain.board.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDeleteReq {
    private Long boardId;
    private Long userId;

    @Builder
    private BoardDeleteReq(Long boardId, Long userId) {
        this.boardId = boardId;
        this.userId = userId;
    }
}
