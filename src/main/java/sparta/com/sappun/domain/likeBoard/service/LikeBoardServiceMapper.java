package sparta.com.sappun.domain.likeBoard.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.board.dto.response.BoardToLikeGetRes;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.likeBoard.dto.response.LikeBoardGetRes;
import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;

@Mapper
public interface LikeBoardServiceMapper {
    LikeBoardServiceMapper INSTANCE = Mappers.getMapper(LikeBoardServiceMapper.class);

    @Mapping(source = "user.nickname", target = "nickname")
    LikeBoardGetRes toLikeBoardGetRes(LikeBoard likeBoard);

    @Mapping(source = "user.nickname", target = "nickname")
    BoardToLikeGetRes toBoardtoLikeGetRes(Board board);
}
