package sparta.com.sappun.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.repository.BoardRepository;
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
import sparta.com.sappun.global.validator.S3Validator;
import sparta.com.sappun.global.validator.UserValidator;
import sparta.com.sappun.infra.s3.S3Util;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final S3Util s3Util;

    @Transactional
    public CommentSaveRes saveComment(CommentSaveReq req, MultipartFile multipartFile) {
        // 보드 아이디 조회 로직
        Board board = findBoard(req.getBoardId());
        // 사용자 아이디 조회 로직
        User user = getUserById(req.getUserId());

        String fileImage = null;
        if (multipartFile != null && !multipartFile.isEmpty()) { // 이미지 입력이 있는 경우
            // 이미지 파일인지 확인
            S3Validator.isProfileImageFile(multipartFile);
            // 이미지 업로드
            fileImage = s3Util.uploadFile(multipartFile, S3Util.FilePath.COMMENT);
        }
        user.updateScore(50); // 댓글 작성하면 점수 +50

        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
                commentRepository.save(
                        Comment.builder()
                                .content(req.getContent())
                                .fileURL(fileImage)
                                .user(user)
                                .board(board)
                                .build()));
    }

    @Transactional
    public CommentUpdateRes updateComment(CommentUpdateReq req, MultipartFile multipartFile) {
        // 댓글이 존재하는지 확인
        Comment comment = findComment(req.getCommentId());
        // 사용자가 존재하는지 확인
        User user = getUserById(req.getUserId());
        // 사용자가 작성자인지 확인
        CommentValidator.checkCommentUser(comment.getUser().getId(), user.getId());


        // 기존 이미지
        String imageURL = comment.getFileURL();
        if (!(imageURL == null) || !imageURL.isEmpty()) {
            // 기존 이미지 삭제
            s3Util.deleteFile(imageURL, S3Util.FilePath.COMMENT);
        }
        // 이미지 파일인지 확인
        S3Validator.isProfileImageFile(multipartFile);
        // 이미지 업로드
        imageURL = s3Util.uploadFile(multipartFile, S3Util.FilePath.COMMENT);

        // 댓글 업데이트 로직
        comment.update(req, imageURL);

        return CommentServiceMapper.INSTANCE.toCommentUpdateRes(comment);
    }

    @Transactional
    public CommentDeleteRes deleteComment(Long commentId, Long userId) {

        // 댓글이 존재하는지 확인
        Comment comment = findComment(commentId);
        // 사용자가 존재하는지 확인
        User user = getUserById(userId);
        // 사용자가 작성자 또는 관리자인지 확인
        CommentValidator.checkCommentUser(comment.getUser(), user);

        // 기존 이미지
        String imageURL = comment.getFileURL();
        // 기존 이미지가 있으면 삭제
        if (!(imageURL == null) || !imageURL.isEmpty()) {
            s3Util.deleteFile(imageURL, S3Util.FilePath.COMMENT);
        }
        user.updateScore(-50); // 댓글 삭제하면 점수 -50

        // 댓글 삭제 로직
        commentRepository.delete(comment);

        return new CommentDeleteRes();
    }

    private Comment findComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId);
        CommentValidator.validate(comment);
        return comment;
    }

    private Board findBoard(Long boardId) {
        Board board = boardRepository.findById(boardId);
        BoardValidator.validate(board);
        return board;
    }

    private User getUserById(Long userId) {
        User user = userRepository.findById(userId);
        UserValidator.validate(user);
        return user;
    }
}
