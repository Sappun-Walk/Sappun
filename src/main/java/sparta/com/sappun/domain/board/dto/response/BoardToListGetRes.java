package sparta.com.sappun.domain.board.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardToListGetRes {
    private Long id;
    private String nickname;
    private String title;
    private String fileURL;
    private String departure;
    private String destination;
    private List<String> stopover;
    private RegionEnum region;
    private Integer likeCount;

    @Builder
    private BoardToListGetRes(
            Long id,
            String nickname,
            String title,
            String fileURL,
            String departure,
            String destination,
            List<String> stopover,
            RegionEnum region,
            Integer likeCount) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.fileURL = fileURL;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
        this.region = region;
        this.likeCount = likeCount;
    }
}
