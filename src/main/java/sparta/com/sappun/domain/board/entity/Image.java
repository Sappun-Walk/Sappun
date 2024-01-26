package sparta.com.sappun.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
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

    public Image(String imageUrl, Board board) {
        this.imageUrl = imageUrl;
        this.board = board;
        if (board != null) {
            board.getImages().add(this); // Board에도 Image 추가
        }
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
