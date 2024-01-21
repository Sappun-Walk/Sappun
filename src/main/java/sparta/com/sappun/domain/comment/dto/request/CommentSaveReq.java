package sparta.com.sappun.domain.comment.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveReq {
    private Long boardId;
    private Long userId;

    private MultipartFile image;

    @Size(max = 500, message = "500자 이내로 입력해주세요.") // TODO: 255글자 이상으로 작성안됨 확인필요 403Error 발생
    private String content;

    @Builder
    private CommentSaveReq(Long boardId, String content) {
        this.boardId = boardId;
        this.content = content;
    }
}
