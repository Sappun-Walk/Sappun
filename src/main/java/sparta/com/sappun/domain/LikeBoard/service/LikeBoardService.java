package sparta.com.sappun.domain.LikeBoard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.com.sappun.domain.LikeBoard.dto.response.LikeBoardSaveRes;
import sparta.com.sappun.domain.LikeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.LikeBoard.repository.LikeBoardRepository;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.BoardValidator;
import sparta.com.sappun.global.validator.UserValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeBoardService {
    private final LikeBoardRepository likeBoardRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public LikeBoardSaveRes likeBoardSaveRes(Long boardId, Long userId) {
        User user = userRepository.findById(userId);
        UserValidator.validate(user);
        Board board = boardRepository.findById(boardId);
        BoardValidator.validate(board);

        board.getUser().updateScore(10); // 좋아요를 받은 게시글의 작성자 점수 +10

        likeBoardRepository.save(LikeBoard.builder().board(board).user(user).build());
        return new LikeBoardSaveRes();
    }
}
