package sparta.com.sappun.domain.board.service;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardSaveRes;
import sparta.com.sappun.domain.board.entity.Board;

@Mapper
public interface BoardServiceMapper {
    BoardServiceMapper INSTANCE = Mappers.getMapper(BoardServiceMapper.class);

    BoardGetRes toBoardGetRes(Board board);

    List<BoardGetRes> toBoardListGetRes(List<Board> boardList);

    List<BoardGetRes> toBoardBestListGetRes(List<Board> boardList);

    BoardSaveRes toBoardSavaRes(Board board);
}
