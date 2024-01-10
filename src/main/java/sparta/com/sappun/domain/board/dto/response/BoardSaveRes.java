package sparta.com.sappun.domain.board.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardSaveRes {

    private Long id;

    @Builder
    private BoardSaveRes(Long id) {
        this.id = id;
    }
}
