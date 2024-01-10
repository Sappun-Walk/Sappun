package sparta.com.sappun.domain.board.dto.request;

import java.util.List;
import lombok.*;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardSaveReq {
    private Long userId;

    private String title;
    private String content;
    private String fileURL;
    private String departure;
    private String destination;
    private List<String> stopover;
    private RegionEnum region;

    @Builder
    private BoardSaveReq(
            String title,
            String content,
            String fileURL,
            String departure,
            String destination,
            List<String> stopover,
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
