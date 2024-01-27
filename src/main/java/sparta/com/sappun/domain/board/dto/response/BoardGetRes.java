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
    private Long id;
    private String nickname;
    private String profileUrl;
    private Long userId;
    private String title;
    private String content;
    private String fileURL;
    private String departure;
    private String destination;
    private List<String> stopover;
    private RegionEnum region;
    private List<ImageGetRes> images;
    private int likeCount;
    private List<CommentGetRes> comments;

    @Builder
    private BoardGetRes(
            Long id,
            String nickname,
            String profileUrl,
            Long userId,
            String title,
            String content,
            String fileURL,
            List<ImageGetRes> images,
            String departure,
            String destination,
            List<String> stopover,
            RegionEnum region,
            List<CommentGetRes> comments,
            Integer likeCount) {
        this.id = id;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.fileURL = fileURL;
        this.images = images;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
        this.region = region;
        this.comments = comments;
        this.likeCount = likeCount;
    }
}
