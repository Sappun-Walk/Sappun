package sparta.com.sappun.domain.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.TimeStamp;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.comment.dto.request.CommentUpdateReq;
import sparta.com.sappun.domain.likeComment.entity.LikeComment;
import sparta.com.sappun.domain.reportComment.entity.ReportComment;
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

    private String fileURL; // 댓글 사진 URL

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeComment> likeComment = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportComment> reportComment = new ArrayList<>();

    @Builder
    private Comment(String content, String fileURL, User user, Board board) {
        this.content = content;
        this.fileURL = fileURL;
        this.user = user;
        this.board = board;
    }

    public void update(CommentUpdateReq req) {
        this.content = req.getContent();
        this.fileURL = req.getFileURL();
    }

    public Integer getLikeCount() {
        return likeComment.size();
    }

    public Integer getReportCount() {
        return reportComment.size();
    }
}
