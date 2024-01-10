package sparta.com.sappun.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.board.dto.request.BoardListGetReq;
import sparta.com.sappun.domain.board.dto.request.BoardSaveReq;
import sparta.com.sappun.domain.board.dto.response.BoardBestListGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardListGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardSaveRes;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.BoardLike;
import sparta.com.sappun.domain.board.entity.RegionEnum;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.sample.dto.request.SampleSaveReq;
import sparta.com.sappun.domain.sample.dto.response.SampleSaveRes;
import sparta.com.sappun.domain.sample.entity.Sample;
import sparta.com.sappun.domain.sample.service.SampleServiceMapper;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.BoardValidator;
import sparta.com.sappun.global.validator.UserValidator;

import java.util.ArrayList;
import java.util.List;

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

//    public List<BoardListGetRes> getBoardList(BoardListGetReq requestDto) {
//        List<Board> boardList = boardRepository.findAllByRegion(requestDto.getRegion());
//        List<BoardListGetRes> res = new ArrayList<>();
//
//        for (Board board : boardList) {
//            res.add(new BoardListGetRes(board));
//        }
//        return res;
//    }

    @Transactional(readOnly = true)
    public BoardListGetRes getBoardList(RegionEnum region) {
//        List<Board> boards = boardRepository.findAllByRegion(region);
//        return new BoardListGetRes(boards);
        return BoardServiceMapper.INSTANCE.toBoardListGetRes(boardRepository.findAllByRegion(region));
    }

    public BoardBestListGetRes getBoardBestList(){
        return BoardServiceMapper.INSTANCE.toBoardBestListGetRes(boardRepository.findTop3ByOrderByBoardLikesDesc());
    }


    @Transactional
    public BoardSaveRes saveBoard(BoardSaveReq boardSaveReq) {
        User user = userRepository.findById(boardSaveReq.getUserId());
        UserValidator.validate(user);

        return BoardServiceMapper.INSTANCE.toBoardSavaRes(
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

    private Board getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId);
        BoardValidator.validate(board);
        return board;
    }
}
