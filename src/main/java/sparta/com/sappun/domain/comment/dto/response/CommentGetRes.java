package sparta.com.sappun.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentGetRes {
    private Long id;
    private String nickname;
    private String content;
    private String fileURL;
    private Integer likeCount;

    @Builder
    private CommentGetRes(
            Long id, String nickname, String content, String fileURL, Integer likeCount) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.fileURL = fileURL;
        this.likeCount = likeCount;
    }
}
