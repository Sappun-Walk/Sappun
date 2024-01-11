package sparta.com.sappun.domain.board.dto.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardUpdateReq {
    private Long boardId;
    private Long userId;

    private String title;
    private String content;
    private String fileURL;
    private String departure;
    private String destination;
    private List<String> stopover;
    private RegionEnum region;

    public BoardUpdateReq(
            Long boardId,
            String title,
            String content,
            String fileURL,
            String departure,
            String destination,
            List<String> stopover,
            RegionEnum region) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.fileURL = fileURL;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
        this.region = region;
    }
}
