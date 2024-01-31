package sparta.com.sappun.domain.board.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRegionGetRes {
    private Long id;
    private String nickname;
    private String title;
    private String fileURL;
    private RegionEnum region;
    private Integer likeCount;
    private Integer reportCount;

    @Builder
    private BoardRegionGetRes(
            Long id,
            String nickname,
            String title,
            String fileURL,
            RegionEnum region,
            Integer likeCount,
            Integer reportCount) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.fileURL = fileURL;
        this.region = region;
        this.likeCount = likeCount;
        this.reportCount = reportCount;
    }
}
