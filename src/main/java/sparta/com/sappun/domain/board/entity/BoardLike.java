package sparta.com.sappun.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_boardlike")
public class BoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "boardId", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Builder
    private BoardLike(Long id, Board board, User user) {
        this.id = id;
        this.board = board;
        this.user = user;
    }
}
