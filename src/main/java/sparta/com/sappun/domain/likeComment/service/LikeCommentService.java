package sparta.com.sappun.domain.likeComment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.likeComment.dto.response.LikeCommentSaveRes;
import sparta.com.sappun.domain.likeComment.entity.LikeComment;
import sparta.com.sappun.domain.likeComment.repository.LikeCommentRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.CommentValidator;
import sparta.com.sappun.global.validator.UserValidator;

@Service
@RequiredArgsConstructor
public class LikeCommentService {

    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private static final Integer LIKE_POINT = 10;

    @Transactional
    public LikeCommentSaveRes clickLikeComment(Long commentId, Long userId) {
        User user = userRepository.findById(userId);
        UserValidator.validate(user);
        Comment comment = commentRepository.findById(commentId);
        CommentValidator.validate(comment);
        LikeCommentSaveRes res;

        if (likeCommentRepository.existsLikeCommentByCommentAndUser(comment, user)) { // 이미 좋아요를 누른 상태라면
            comment.getUser().updateScore(-LIKE_POINT); // 좋아요를 받은 댓글의 작성자 점수 -10
            likeCommentRepository.deleteLikeCommentByCommentAndUser(comment, user); // 좋아요 삭제
            comment.clickLikeComment(-1);
            res = LikeCommentSaveRes.builder().isLiked(false).build();
        } else { // 좋아요를 안 누른 상태라면
            comment.getUser().updateScore(LIKE_POINT); // 좋아요를 받은 댓글의 작성자 점수 +10
            likeCommentRepository.save(
                    LikeComment.builder().comment(comment).user(user).build()); // 좋아요 저장
            comment.clickLikeComment(1);
            res = LikeCommentSaveRes.builder().isLiked(true).build();
        }

        return res;
    }
}
