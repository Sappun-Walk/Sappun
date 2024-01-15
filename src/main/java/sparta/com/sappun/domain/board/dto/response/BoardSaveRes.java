package sparta.com.sappun.domain.board.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardSaveRes {
    private String nickname;
    private Long id;
    private String title;
    private String content;
    private String fileURL;
    private String departure;
    private String destination;
    private List<String> stopover;
    private RegionEnum region;

    @Builder
    private BoardSaveRes(
            String nickname,
            Long id,
            String title,
            String content,
            String fileURL,
            String departure,
            String destination,
            List<String> stopover,
            RegionEnum region) {
        this.nickname = nickname;
        this.id = id;
        this.title = title;
        this.content = content;
        this.fileURL = fileURL;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
        this.region = region;
    }
}
