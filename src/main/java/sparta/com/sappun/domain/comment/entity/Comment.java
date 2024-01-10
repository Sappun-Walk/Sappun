package sparta.com.sappun.domain.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.LikeComment.entity.LikeComment;
import sparta.com.sappun.domain.TimeStamp;
import sparta.com.sappun.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_comment")
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // commentId

    private String nickname; // 사용자 닉네임

    @NotBlank private String content; // 댓글 내용

    private String fileUrl; // 댓글 사진 URL

    //    @ManyToOne
    //    @JoinColumn(name = "boardId")
    //    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    private Comment(String content, String fileUrl, User user) {
        this.content = content;
        this.fileUrl = fileUrl;
        this.user = user;
    }
}
