package sparta.com.sappun.domain.board.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardToReportGetRes {
    private Long id;
    private String nickname;
    private String title;
    private String fileURL;
    private String departure;
    private String destination;
    private List<String> stopover;
    private RegionEnum region;
    private Integer likeCount;
    private Integer reportCount;

    @Builder
    private BoardToReportGetRes(
            Long id,
            String nickname,
            String title,
            String fileURL,
            String departure,
            String destination,
            List<String> stopover,
            RegionEnum region,
            Integer likeCount,
            Integer reportCount) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.fileURL = fileURL;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
        this.region = region;
        this.likeCount = likeCount;
        this.reportCount = reportCount;
    }
}
