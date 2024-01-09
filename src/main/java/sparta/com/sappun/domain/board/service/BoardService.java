package sparta.com.sappun.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.board.dto.request.BoardListGetReq;
import sparta.com.sappun.domain.board.dto.request.BoardSaveReq;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardListGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardSaveRes;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.RegionEnum;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.sample.dto.request.SampleSaveReq;
import sparta.com.sappun.domain.sample.dto.response.SampleSaveRes;
import sparta.com.sappun.domain.sample.entity.Sample;
import sparta.com.sappun.domain.sample.service.SampleServiceMapper;
import sparta.com.sappun.global.validator.BoardValidator;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public BoardGetRes getBoard(Long boardId) {
        Board board = getBoardById(boardId);

        return BoardServiceMapper.INSTANCE.toBoardGetRes(board);
    }


    public List<BoardListGetRes> getBoardList(BoardListGetReq requestDto) {
        List<Board> boardList = boardRepository.findAllByRegion(requestDto.getRegion());
        List<BoardListGetRes> res = new ArrayList<>();

        for (Board board : boardList) {
            res.add(new BoardListGetRes(board));
        }
        return res;
    }
    //Service Mapper 및 Builder 적용 중
//    @Transactional(readOnly = true)
//    public BoardListGetRes getBoardList(String title, String content, String fileURL, String departure, String destination, String stopover,RegionEnum region) {
//
//        List<BoardListGetRes> boardListGetRes = BoardServiceMapper.INSTANCE.toBoardListGetRes(
//                boardRepository.findAllByRegion(region));
//        return BoardListGetRes.builder()
//                .title(title)
//                .content(content)
//                .fileURL(fileURL)
//                .departure(departure)
//                .destination(destination)
//                .stopover(stopover)
//                .region(region)
//                .build();
//    }

    @Transactional
    public BoardSaveRes saveBoard(BoardSaveReq boardSaveReq) {
        //validator 추가
        
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
                                .build()));
    }

    private Board getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId);
        BoardValidator.validate(board);
        return board;
    }
}
