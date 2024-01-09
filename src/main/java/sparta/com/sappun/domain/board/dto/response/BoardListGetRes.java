package sparta.com.sappun.domain.board.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardListGetRes {
    private String title;
    private String content;
    private String fileURL;
    private String departure;
    private String destination;
    private String stopover;
    private RegionEnum region;

    @Builder
    public BoardListGetRes(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.fileURL = board.getFileURL();
        this.departure = board.getDeparture();
        this.destination = board.getDestination();
        this.stopover = board.getStopover();
        this.region = board.getRegion();
    }
}
