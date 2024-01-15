package sparta.com.sappun.domain.board.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.TimeStamp;
import sparta.com.sappun.domain.board.dto.request.BoardUpdateReq;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.reportBoard.entity.ReportBoard;
import sparta.com.sappun.domain.user.entity.User;

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

    @ElementCollection private List<String> stopover;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RegionEnum region;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeBoard> likeBoard = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportBoard> reportBoard = new ArrayList<>();

    @Builder
    private Board(
            Long id,
            String title,
            String content,
            String fileURL,
            String departure,
            String destination,
            List<String> stopover,
            RegionEnum region,
            User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.fileURL = fileURL;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
        this.region = region;
        this.user = user;
    }

    public void update(BoardUpdateReq boardUpdateReq) {
        this.title = boardUpdateReq.getTitle();
        this.content = boardUpdateReq.getContent();
        this.fileURL = boardUpdateReq.getFileURL();
        this.departure = boardUpdateReq.getDeparture();
        this.destination = boardUpdateReq.getDestination();
        this.stopover = boardUpdateReq.getStopover();
    }

    public Integer getLikeCount() {
        return likeBoard.size();
    }

    public Integer getReportCount() {
        return reportBoard.size();
    }
}
