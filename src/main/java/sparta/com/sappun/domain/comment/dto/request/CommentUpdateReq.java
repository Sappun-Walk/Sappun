package sparta.com.sappun.domain.comment.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateReq {
    private Long commentId;
    private Long userId;

    @Size(max = 255, message = "255자 이내로 입력해주세요.") // TODO: 255글자 이상으로 작성안됨 확인필요 403Error 발생
    private String content;

    private MultipartFile image;

    @Builder
    private CommentUpdateReq(String content) {
        this.content = content;
    }
}
