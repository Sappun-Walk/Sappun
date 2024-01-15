package sparta.com.sappun.domain.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Page<BoardGetRes> getBoardList(
            RegionEnum region, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Board> boardList = boardRepository.findAllByRegion(region, pageable);
        return boardList.map(BoardServiceMapper.INSTANCE::toBoardPageGetRes);
    }

    @Transactional(readOnly = true)
    public BoardBestListGetRes getBoardBestList() {
        List<BoardToListGetRes> boardGetRes =
                BoardServiceMapper.INSTANCE.toBoardBestListGetRes(
                        boardRepository.findTop3ByOrderByLikeCountDesc());
        return BoardBestListGetRes.builder().boards(boardGetRes).build();
    }

    @Transactional
    public BoardSaveRes saveBoard(BoardSaveReq boardSaveReq) {
        User user = getUserById(boardSaveReq.getUserId());
        user.updateScore(100); // 게시글 작성하면 점수 +100

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
                                .likeCount(0)
                                .reportCount(0)
                                .user(user)
                                .build()));
    }

    @Transactional
    public BoardUpdateRes updateBoard(BoardUpdateReq boardUpdateReq) {
        Board board = getBoardById(boardUpdateReq.getBoardId());
        User user = getUserById(boardUpdateReq.getUserId());
        BoardValidator.checkBoardUser(board.getUser().getId(), user.getId()); // 수정 가능한 사용자인지 확인
        board.update(boardUpdateReq);

        return BoardServiceMapper.INSTANCE.toBoardUpdateRes(board);
    }

    @Transactional
    public BoardDeleteRes deleteBoard(Long boardId, Long userId) {
        Board board = getBoardById(boardId);
        User user = getUserById(userId);
        BoardValidator.checkBoardUser(board.getUser(), user); // 삭제 가능한 사용자인지 확인

        user.updateScore(-100); // 게시글 삭제하면 점수 -100

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
}
