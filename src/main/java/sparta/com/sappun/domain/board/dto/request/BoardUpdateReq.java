package sparta.com.sappun.domain.board.dto.request;

import java.util.List;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardUpdateReq {
    private Long boardId;
    private Long userId;

    private String title;
    private String content;
    private String departure;
    private String destination;
    private List<String> stopover;
    private RegionEnum region;
    private String image;
    private List<MultipartFile> photoImages;

    @Builder
    private BoardUpdateReq(
            Long boardId,
            String title,
            String content,
            String departure,
            String destination,
            List<String> stopover,
            RegionEnum region,
            String image) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
        this.region = region;
        this.image = image;
    }
}
