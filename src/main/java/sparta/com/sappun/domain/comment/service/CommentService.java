package sparta.com.sappun.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
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
}