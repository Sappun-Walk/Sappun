package sparta.com.sappun.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.TimeStamp;
import sparta.com.sappun.domain.board.dto.request.BoardUpdateReq;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_board")
public class Board extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String fileURL;

    @Column(nullable = false)
    private String departure;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private String stopover;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RegionEnum region;


    @Builder
    public Board(
            Long id,
            String title,
            String content,
            String fileURL,
            String departure,
            String destination,
            String stopover,
            RegionEnum region) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.fileURL = fileURL;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
        this.region = region;
    }

    public void update(BoardUpdateReq boardUpdateReq) {
        this.title = boardUpdateReq.getTitle();
        this.content = boardUpdateReq.getContent();
        this.fileURL = boardUpdateReq.getFileURL();
        this.departure = boardUpdateReq.getDeparture();
        this.destination = boardUpdateReq.getDestination();
        this.stopover = boardUpdateReq.getStopover();
    }
}
