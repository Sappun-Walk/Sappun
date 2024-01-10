package sparta.com.sappun.domain.board.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardBestListGetRes {
    private List<BoardGetRes> boards;
}
