package sparta.com.sappun.domain.board.service;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardSaveRes;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.comment.dto.response.CommentGetRes;
import sparta.com.sappun.domain.comment.entity.Comment;

@Mapper
public interface BoardServiceMapper {
    BoardServiceMapper INSTANCE = Mappers.getMapper(BoardServiceMapper.class);

    @Mapping(source = "user.nickname", target = "nickname")
    BoardGetRes toBoardGetRes(Board board);

    List<BoardGetRes> toBoardListGetRes(List<Board> boardList);

    List<BoardGetRes> toBoardBestListGetRes(List<Board> boardList);

    @Mapping(source = "user.nickname", target = "nickname")
    BoardSaveRes toBoardSaveRes(Board board);

    @Mapping(source = "user.nickname", target = "nickname")
    CommentGetRes toCommentGetRes(Comment comment);
}
