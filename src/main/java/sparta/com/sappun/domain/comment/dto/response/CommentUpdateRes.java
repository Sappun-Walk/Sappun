package sparta.com.sappun.domain.comment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateRes {
    private Long id;
    private String nickname;
    private String content;
    private String fileUrl;

    @Builder
    public CommentUpdateRes(Long id, String nickname, String content, String fileUrl) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.fileUrl = fileUrl;
    }
}
