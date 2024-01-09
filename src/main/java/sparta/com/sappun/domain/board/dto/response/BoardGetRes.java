package sparta.com.sappun.domain.board.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardGetRes {
    private String title;
    private String content;
    private String fileURL;
    private String departure;
    private String destination;
    private String stopover;
    private RegionEnum region;

    @Builder
    public BoardGetRes(
            String title,
            String content,
            String fileURL,
            String departure,
            String destination,
            String stopover,
            RegionEnum region) {
        this.title = title;
        this.content = content;
        this.fileURL = fileURL;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
        this.region = region;
    }
}
