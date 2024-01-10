package sparta.com.sappun.domain.board.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.board.dto.response.BoardBestListGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardListGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardSaveRes;
import sparta.com.sappun.domain.board.entity.Board;

import java.util.List;

@Mapper
public interface BoardServiceMapper {
    BoardServiceMapper INSTANCE = Mappers.getMapper(BoardServiceMapper.class);

    BoardGetRes toBoardGetRes(Board board);

    BoardListGetRes toBoardListGetRes(List<Board> boardList);

    BoardBestListGetRes toBoardBestListGetRes(List<Board> boardList);

    BoardSaveRes toBoardSavaRes(Board board);
}
