package sparta.com.sappun.domain.board.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardBestListGetRes {
    private List<BoardGetRes> boards;

    @Builder
    public BoardBestListGetRes(List<BoardGetRes> boards) {
        this.boards = boards;
    }
}
