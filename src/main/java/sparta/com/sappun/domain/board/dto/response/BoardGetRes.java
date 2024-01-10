package sparta.com.sappun.domain.board.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.board.entity.RegionEnum;
import sparta.com.sappun.domain.comment.dto.response.CommentGetRes;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardGetRes {
    private String nickname;
    private String title;
    private String content;
    private String fileURL;
    private String departure;
    private String destination;
    private List<String> stopover;
    private RegionEnum region;

    private List<CommentGetRes> comments;

    @Builder
    private BoardGetRes(
            String nickname,
            String title,
            String content,
            String fileURL,
            String departure,
            String destination,
            List<String> stopover,
            RegionEnum region,
            List<CommentGetRes> comments) {
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.fileURL = fileURL;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
        this.region = region;
        this.comments = comments;
    }
}
