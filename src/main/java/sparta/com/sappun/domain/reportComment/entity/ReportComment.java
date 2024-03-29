package sparta.com.sappun.domain.reportComment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import sparta.com.sappun.domain.TimeStamp;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_commentreport")
public class ReportComment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String reason;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "commentId", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Builder
    private ReportComment(Comment comment, User user, String reason) {
        this.comment = comment;
        this.user = user;
        this.reason = reason;
    }
}
