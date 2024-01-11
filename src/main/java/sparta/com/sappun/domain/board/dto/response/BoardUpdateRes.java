package sparta.com.sappun.domain.board.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardUpdateRes {
    private String nickname;
    private Long id;
    private String title;
    private String content;
    private String fileURL;
    private String departure;
    private String destination;
    private List<String> stopover;

    public BoardUpdateRes(
            String nickname,
            Long id,
            String title,
            String content,
            String fileURL,
            String departure,
            String destination,
            List<String> stopover) {
        this.nickname = nickname;
        this.id = id;
        this.title = title;
        this.content = content;
        this.fileURL = fileURL;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
    }
}
