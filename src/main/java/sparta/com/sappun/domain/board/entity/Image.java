package sparta.com.sappun.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    // 생성자, 빌더 등 필요한 메서드 추가

    @Builder
    private Image(String imageUrl, Board board) {
        this.imageUrl = imageUrl;
        this.board = board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
