package sparta.com.sappun.domain.board.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageGetRes {
    private Long id;
    private String imageUrl;

    public ImageGetRes(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }
}
