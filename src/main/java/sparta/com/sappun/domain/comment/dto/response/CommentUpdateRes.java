package sparta.com.sappun.domain.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@JsonIgnoreProperties
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
