package sparta.com.sappun.domain.comment.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveReq {
    //    private Long BoardId;
    private String nickname;

    @Size(max = 500, message = "500자 이내로 입력해주세요.")
    private String content;

    private String fileUrl;

    @Builder
    private CommentSaveReq(String nickname, String content, String fileUrl) {
        this.nickname = nickname;
        this.content = content;
        this.fileUrl = fileUrl;
    }
}