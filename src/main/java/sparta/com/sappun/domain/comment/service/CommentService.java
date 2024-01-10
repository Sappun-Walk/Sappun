package sparta.com.sappun.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.comment.dto.request.CommentDeleteReq;
import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.dto.request.CommentUpdateReq;
import sparta.com.sappun.domain.comment.dto.response.CommentDeleteRes;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.dto.response.CommentUpdateRes;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.CommentValidator;
import sparta.com.sappun.global.validator.UserValidator;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentSaveRes saveComment(CommentSaveReq commentSaveReq) {
        // 보드 아이디 조회 로직 구현 필요
        User user = userRepository.findById(commentSaveReq.getUserId());
        UserValidator.validate(user);

        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
                commentRepository.save(
                        Comment.builder()
                                .content(commentSaveReq.getContent())
                                .fileUrl(commentSaveReq.getFileUrl())
                                .user(user)
                                .build()));
    }

    @Transactional
    public CommentUpdateRes updateComment(CommentUpdateReq commentUpdateReq) {
        // 댓글이 존재하는지 확인
        Comment comment = findComment(commentUpdateReq.getCommentId());

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(commentUpdateReq.getUserId());
        UserValidator.validate(user);

        // 댓글 업데이트 로직
        comment.update(commentUpdateReq);

        return CommentServiceMapper.INSTANCE.toCommentUpdateRes(comment);
    }

    @Transactional
    public CommentDeleteRes deleteComment(CommentDeleteReq commentDeleteReq) {
        // 댓글이 존재하는지 확인
        Comment comment = findComment(commentDeleteReq.getCommentId());

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(commentDeleteReq.getUserId());
        UserValidator.validate(user);

        // 댓글 삭제 로직
        commentRepository.delete(comment);
        return new CommentDeleteRes();
    }

    private Comment findComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId);
        CommentValidator.validate(comment);
        return comment;
    }
}
