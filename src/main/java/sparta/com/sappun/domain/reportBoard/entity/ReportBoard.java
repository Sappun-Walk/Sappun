package sparta.com.sappun.domain.reportBoard.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import sparta.com.sappun.domain.TimeStamp;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_boardreport")
public class ReportBoard extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String reason;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "boardId", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Builder
    private ReportBoard(Board board, User user, String reason) {
        this.board = board;
        this.user = user;
        this.reason = reason;
    }
}
