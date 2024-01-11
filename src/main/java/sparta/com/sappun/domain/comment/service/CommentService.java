package sparta.com.sappun.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.repository.BoardRepository;
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
import sparta.com.sappun.global.validator.BoardValidator;
import sparta.com.sappun.global.validator.CommentValidator;
import sparta.com.sappun.global.validator.UserValidator;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentSaveRes saveComment(CommentSaveReq req) {
        // 보드 아이디 조회 로직
        Board board = boardRepository.findById(req.getBoardId());
        BoardValidator.validate(board);
        // 사용자 아이디 조회 로직
        User user = userRepository.findById(req.getUserId());
        UserValidator.validate(user);

        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
                commentRepository.save(
                        Comment.builder()
                                .content(req.getContent())
                                .fileUrl(req.getFileUrl())
                                .user(user)
                                .board(board)
                                .build()));
    }

    @Transactional
    public CommentUpdateRes updateComment(CommentUpdateReq req) {
        // 댓글이 존재하는지 확인
        Comment comment = findComment(req.getCommentId());

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(req.getUserId());
        UserValidator.validate(user);

        // 사용자가 작성자 또는 관리자인지 확인
        CommentValidator.checkCommentUser(comment.getUser().getId(), req.getUserId());

        // 댓글 업데이트 로직
        comment.update(req);

        return CommentServiceMapper.INSTANCE.toCommentUpdateRes(comment);
    }

    @Transactional
    public CommentDeleteRes deleteComment(CommentDeleteReq req) {
        // 댓글이 존재하는지 확인
        Comment comment = findComment(req.getCommentId());

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(req.getUserId());
        UserValidator.validate(user);

        // 사용자가 작성자 또는 관리자인지 확인
        CommentValidator.checkCommentUser(comment.getUser().getId(), req.getUserId());

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
