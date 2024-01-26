package sparta.com.sappun.domain.board.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageGetRes {
    private Long id;
    private String imageUrl;

    // 생성자, 빌더 등 필요한 메서드 추가

    public ImageGetRes(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }
}