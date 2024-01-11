package sparta.com.sappun.domain.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.board.dto.request.BoardDeleteReq;
import sparta.com.sappun.domain.board.dto.request.BoardSaveReq;
import sparta.com.sappun.domain.board.dto.request.BoardUpdateReq;
import sparta.com.sappun.domain.board.dto.response.*;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.RegionEnum;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.BoardValidator;
import sparta.com.sappun.global.validator.UserValidator;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public BoardGetRes getBoard(Long boardId) {
        Board board = getBoardById(boardId);

        return BoardServiceMapper.INSTANCE.toBoardGetRes(board);
    }

    @Transactional(readOnly = true)
    public BoardListGetRes getBoardList(RegionEnum region) {
        //        List<Board> boards = boardRepository.findAllByRegion(region);
        //        return new BoardListGetRes(boards);
        //        return
        // BoardServiceMapper.INSTANCE.toBoardListGetRes(boardRepository.findAllByRegion(region));
        List<BoardGetRes> boardGetRes =
                BoardServiceMapper.INSTANCE.toBoardListGetRes(boardRepository.findAllByRegion(region));
        return BoardListGetRes.builder().boards(boardGetRes).build();
    }

    @Transactional(readOnly = true)
    public BoardBestListGetRes getBoardBestList() {
        List<BoardGetRes> boardGetRes =
                BoardServiceMapper.INSTANCE.toBoardBestListGetRes(
                        boardRepository.findTop3ByOrderByLikeBoardDesc());
        return BoardBestListGetRes.builder().boards(boardGetRes).build();
    }

    @Transactional
    public BoardSaveRes saveBoard(BoardSaveReq boardSaveReq) {
        User user = getUserById(boardSaveReq.getUserId());

        return BoardServiceMapper.INSTANCE.toBoardSaveRes(
                boardRepository.save(
                        Board.builder()
                                .title(boardSaveReq.getTitle())
                                .content(boardSaveReq.getContent())
                                .fileURL(boardSaveReq.getFileURL())
                                .departure(boardSaveReq.getDeparture())
                                .destination(boardSaveReq.getDestination())
                                .stopover(boardSaveReq.getStopover())
                                .region(boardSaveReq.getRegion())
                                .user(user)
                                .build()));
    }

    @Transactional
    public BoardUpdateRes updateBoard(BoardUpdateReq boardUpdateReq) {
        Board board = findBoard(boardUpdateReq.getBoardId());
        User user = getUserById(boardUpdateReq.getUserId());
        BoardValidator.checkBoardUser(board.getUser().getId(), user.getId());
        board.update(boardUpdateReq);

        return BoardServiceMapper.INSTANCE.toBoardUpdateRes(board);
    }

    @Transactional
    public BoardDeleteRes deleteBoard(BoardDeleteReq boardDeleteReq) {
        Board board = findBoard(boardDeleteReq.getBoardId());
        User user = getUserById(boardDeleteReq.getUserId());
        BoardValidator.checkBoardUser(board.getUser().getId(), user.getId());
        boardRepository.delete(board);

        return new BoardDeleteRes();
    }

    private Board getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId);
        BoardValidator.validate(board);
        return board;
    }

    private User getUserById(Long userId) {
        User user = userRepository.findById(userId);
        UserValidator.validate(user);
        return user;
    }

    private Board findBoard(Long boardId) {
        Board board = boardRepository.findById(boardId);
        BoardValidator.validate(board);
        return board;
    }
}
