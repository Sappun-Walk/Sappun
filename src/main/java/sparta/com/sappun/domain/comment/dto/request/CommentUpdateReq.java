package sparta.com.sappun.domain.comment.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateReq {
    private Long commentId;
    private Long userId;

    @Size(max = 500, message = "500자 이내로 입력해주세요.") // TODO: 255글자 이상으로 작성안됨 확인필요 403Error 발생
    private String content;

    private String fileUrl;

    @Builder
    private CommentUpdateReq(String content, String fileUrl) {
        this.content = content;
        this.fileUrl = fileUrl;
    }
}
