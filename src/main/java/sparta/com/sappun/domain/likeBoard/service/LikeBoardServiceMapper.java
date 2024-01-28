package sparta.com.sappun.domain.likeBoard.service;

import org.mapstruct.Context;
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
    LikeBoardGetRes toLikeBoardGetRes(LikeBoard likeBoard, @Context boolean isLikedByUser);

    @Mapping(source = "user.nickname", target = "nickname")
    BoardToLikeGetRes toBoardtoLikeGetRes(Board board);

    default boolean isLikedByUser(LikeBoard likeBoard, @Context boolean isLikedByUser) {
        // 여기에 로그인한 유저가 해당 글에 좋아요를 눌렀는지 여부를 판단하는 로직을 추가
        // 예를 들어, likeBoard에 해당 유저의 정보가 들어있는지 여부를 확인하면 됩니다.
        return isLikedByUser && likeBoard != null && likeBoard.getUser() != null;
    }
}
