package sparta.com.sappun.domain.board.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardLikeGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardListGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardRegionGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardUserGetRes;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.comment.dto.response.CommentGetRes;
import sparta.com.sappun.domain.comment.entity.Comment;

@Mapper
public interface BoardServiceMapper {
    BoardServiceMapper INSTANCE = Mappers.getMapper(BoardServiceMapper.class);

    @Mapping(source = "user.nickname", target = "nickname")
    @Mapping(source = "user.profileUrl", target = "profileUrl")
    @Mapping(source = "user.id", target = "userId")
    BoardGetRes toBoardGetRes(Board board);

    @Mapping(source = "user.nickname", target = "nickname")
    @Mapping(source = "user.profileUrl", target = "profileUrl")
    @Mapping(source = "user.id", target = "userId")
    CommentGetRes toCommentGetRes(Comment comment);

    @Mapping(source = "user.nickname", target = "nickname")
    BoardListGetRes toBoardListGetRes(Board board);

    @Mapping(source = "user.nickname", target = "nickname")
    BoardLikeGetRes toBoardLikeGetRes(Board board);

    @Mapping(source = "user.nickname", target = "nickname")
    BoardRegionGetRes toBoardRegionGetRes(Board board);

    @Mapping(source = "user.nickname", target = "nickname")
    BoardUserGetRes toBoardUserGetRes(Board board);
}
