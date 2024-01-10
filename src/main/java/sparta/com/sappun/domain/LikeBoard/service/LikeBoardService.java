package sparta.com.sappun.domain.LikeBoard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.com.sappun.domain.LikeBoard.dto.response.LikeBoardSaveRes;
import sparta.com.sappun.domain.LikeBoard.repository.LikeBoardRepository;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.BoardLike;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
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
        Board board = boardRepository.findById(boardId);
        // CommentValidator.validate(comment);
        UserValidator.validate(user);
        likeBoardRepository.save(BoardLike.builder().board(board).user(user).build());
        return new LikeBoardSaveRes();
    }
}
