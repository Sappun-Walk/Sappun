package sparta.com.sappun.domain.board.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;
import sparta.com.sappun.domain.board.entity.Board;

@Mapper
public interface BoardServiceMapper {
    BoardServiceMapper INSTANCE = Mappers.getMapper(BoardServiceMapper.class);

    BoardGetRes toBoradGetRes(Board board);
}
