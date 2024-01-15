package sparta.com.sappun.domain.likeComment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.TimeStamp;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.user.entity.User;

@Entity
@Getter
@Table(name = "tb_commentlike")
@NoArgsConstructor
public class LikeComment extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    private LikeComment(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }
}
