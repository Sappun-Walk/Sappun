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
import sparta.com.sappun.global.validator.UserValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeCommentService {

    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    //    public void saveLike(Long commentId, UserDetailsImpl userDetails) {
    //        Comment comment = commentRepository.findById(commentId);
    //
    //        User user = userDetails.getUser();
    //        LikeComment commentLike = new LikeComment();
    //
    //        commentLike.setComment(comment);
    //        commentLike.setUser(user);
    //
    //        likeCommentRepository.save(commentLike);
    //    }
    @Transactional
    public LikeCommentSaveRes likeCommentSaveRes(Long commentid, Long userId) {
        User user = userRepository.findById(userId);
        Comment comment = commentRepository.findById(commentid);
        //CommentValidator.validate(comment);
        UserValidator.validate(user);
        likeCommentRepository.save(LikeComment.builder().comment(comment).user(user).build());
        return new LikeCommentSaveRes();
    }
}
