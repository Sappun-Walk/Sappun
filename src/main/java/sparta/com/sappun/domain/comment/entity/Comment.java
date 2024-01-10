package sparta.com.sappun.domain.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.TimeStamp;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_comment")
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // commentId

    @NotBlank private String content; // 댓글 내용

    private String fileUrl; // 댓글 사진 URL

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    private Comment(Long id, String content, String fileUrl, User user) {
        this.id = id;
        this.content = content;
        this.fileUrl = fileUrl;
        this.user = user;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
