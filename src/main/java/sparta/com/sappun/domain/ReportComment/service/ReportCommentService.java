package sparta.com.sappun.domain.ReportComment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.com.sappun.domain.ReportComment.dto.request.ReportCommentReq;
import sparta.com.sappun.domain.ReportComment.dto.response.ReportCommentRes;
import sparta.com.sappun.domain.ReportComment.entity.ReportComment;
import sparta.com.sappun.domain.ReportComment.repository.ReportCommentRepository;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
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
    public ReportCommentRes reportComment(Long commentId, ReportCommentReq req) {
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
}
