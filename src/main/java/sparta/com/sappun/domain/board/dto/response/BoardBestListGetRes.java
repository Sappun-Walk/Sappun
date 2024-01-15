package sparta.com.sappun.domain.board.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardBestListGetRes {
    private List<BoardToListGetRes> boards;

    @Builder
    private BoardBestListGetRes(List<BoardToListGetRes> boards) {
        this.boards = boards;
    }
}
