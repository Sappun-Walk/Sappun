package sparta.com.sappun.domain.LikeComment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.com.sappun.domain.LikeComment.dto.response.LikeCommentSaveRes;
import sparta.com.sappun.domain.LikeComment.entity.LikeComment;
import sparta.com.sappun.domain.LikeComment.repository.LikeCommentRepository;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.CommentValidator;
import sparta.com.sappun.global.validator.UserValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeCommentService {

    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public LikeCommentSaveRes likeCommentSaveRes(Long commentId, Long userId) {
        User user = userRepository.findById(userId);
        UserValidator.validate(user);
        Comment comment = commentRepository.findById(commentId);
        CommentValidator.validate(comment);

        comment.getUser().updateScore(10); // 좋아요를 받은 댓글의 작성자 점수 +10

        likeCommentRepository.save(LikeComment.builder().comment(comment).user(user).build());
        return new LikeCommentSaveRes();
    }
}
