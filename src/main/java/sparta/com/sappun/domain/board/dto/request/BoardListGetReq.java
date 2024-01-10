package sparta.com.sappun.domain.board.dto.request;

import lombok.*;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardListGetReq {
    private RegionEnum region;

    public BoardListGetReq(RegionEnum region) {
        this.region = region;
    }
}
