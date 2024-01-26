package sparta.com.sappun.domain.board.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import sparta.com.sappun.domain.TimeStamp;
import sparta.com.sappun.domain.board.dto.request.BoardUpdateReq;
import sparta.com.sappun.domain.comment.entity.Comment;
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

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> stopover = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RegionEnum region;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column private Integer likeCount;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Column private Integer reportCount;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();  // 여러 이미지를 저장하기 위한 리스트

    @Builder
    private Board(
            String title,
            String content,
            String fileURL,
            List<Image> images,
            String departure,
            String destination,
            List<String> stopover,
            RegionEnum region,
            User user,
            Integer likeCount,
            Integer reportCount) {
        this.title = title;
        this.content = content;
        this.fileURL = fileURL;
        this.images = images;
        this.departure = departure;
        this.destination = destination;
        this.stopover = stopover;
        this.region = region;
        this.user = user;
        this.likeCount = likeCount;
        this.reportCount = reportCount;
    }

    public void update(BoardUpdateReq boardUpdateReq, String fileURL, List<Image> images) {
        this.title = boardUpdateReq.getTitle();
        this.content = boardUpdateReq.getContent();
        this.fileURL = fileURL;
        this.departure = boardUpdateReq.getDeparture();
        this.destination = boardUpdateReq.getDestination();
        this.stopover = boardUpdateReq.getStopover();
        this.region = boardUpdateReq.getRegion();
    }

    public void clickLikeBoard(Integer count) {
        this.likeCount += count;
    }

    public void clickReportBoard(Integer count) {
        this.reportCount += count;
    }
}
