package sparta.com.sappun.domain.comment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveRes {
    private Long id;
    private String nickname;
    private String content;
    private String fileURL;
    private Integer likeCount;
    private Integer reportCount;

    @Builder
    private CommentSaveRes(
            Long id,
            String nickname,
            String content,
            String fileURL,
            Integer likeCount,
            Integer reportCount) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.fileURL = fileURL;
        this.likeCount = likeCount;
        this.reportCount = reportCount;
    }
}
