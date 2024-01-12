package sparta.com.sappun.domain.ReportComment.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.com.sappun.domain.ReportBoard.dto.response.DeleteReportBoardRes;
import sparta.com.sappun.domain.ReportComment.dto.request.ReportCommentReq;
import sparta.com.sappun.domain.ReportComment.dto.response.DeleteReportCommentRes;
import sparta.com.sappun.domain.ReportComment.dto.response.ReportCommentRes;
import sparta.com.sappun.domain.ReportComment.entity.ReportComment;
import sparta.com.sappun.domain.ReportComment.repository.ReportCommentRepository;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.BoardValidator;
import sparta.com.sappun.global.validator.CommentValidator;
import sparta.com.sappun.global.validator.UserValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportCommentService {
    private final ReportCommentRepository reportCommentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReportCommentRes reportCommentRes(Long commentId, ReportCommentReq req) {
        Comment comment = commentRepository.findById(commentId);
        CommentValidator.validate(comment);
        User user = userRepository.findById(req.getUserId());
        UserValidator.validate(user);

        ReportComment reportComment =
                ReportComment.builder().reason(req.getReason()).comment(comment).user(user).build();

        comment.getUser().updateScore(-50); // 신고를 받은 댓글의 작성자 점수 -50

        reportCommentRepository.save(reportComment);

        return ReportCommentServiceMapper.INSTANCE.toReportCommentRes(reportComment);
    }

    @Transactional
    public DeleteReportCommentRes deleteReportComment(Long commentId) {
        Comment comment = findCommentById(commentId);

        // 허위 신고한 사용자의 점수 -50
        int count = 0; // 신고 횟수
        List<User> reporters = reportCommentRepository.selectUserByComment(comment);
        for(User user : reporters) {
            user.updateScore(-50);
            count++;
        }

        // 신고 취소된 게시글의 작성자의 점수 +50 * 신고 횟수
        comment.getUser().updateScore(50 * count);

        // 신고 내역 삭제
        reportCommentRepository.clearReportCommentByComment(comment);

        return new DeleteReportCommentRes();
    }

    private Comment findCommentById(Long commentId){
        Comment comment = commentRepository.findById(commentId);
        CommentValidator.validate(comment);
        return comment;
    }
}
