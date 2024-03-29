package sparta.com.sappun.domain.likeBoard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.likeBoard.dto.response.LikeBoardSaveRes;
import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.likeBoard.repository.LikeBoardRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.BoardValidator;
import sparta.com.sappun.global.validator.UserValidator;

@Service
@RequiredArgsConstructor
public class LikeBoardService {
    private final LikeBoardRepository likeBoardRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private static final Integer LIKE_POINT = 10;

    @Transactional
    public LikeBoardSaveRes clickLikeBoard(Long boardId, Long userId) {
        User user = userRepository.findById(userId);
        UserValidator.validate(user);
        Board board = boardRepository.findById(boardId);
        BoardValidator.validate(board);
        LikeBoardSaveRes res;

        if (likeBoardRepository.existsLikeBoardByBoardAndUser(board, user)) { // 이미 좋아요를 누른 상태라면
            board.getUser().updateScore(-LIKE_POINT); // 좋아요를 받은 게시글의 작성자 점수 -10
            likeBoardRepository.deleteLikeBoardByBoardAndUser(board, user); // 좋아요 삭제
            board.clickLikeBoard(-1);
            res = LikeBoardSaveRes.builder().isLiked(false).build();
        } else { // 좋아요를 안 누른 상태라면
            board.getUser().updateScore(LIKE_POINT); // 좋아요를 받은 게시글의 작성자 점수 +10
            likeBoardRepository.save(LikeBoard.builder().board(board).user(user).build()); // 좋아요 저장
            board.clickLikeBoard(1);
            res = LikeBoardSaveRes.builder().isLiked(true).build();
        }

        return res;
    }
}
