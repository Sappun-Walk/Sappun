package sparta.com.sappun.domain.comment.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.dto.response.CommentUpdateRes;
import sparta.com.sappun.domain.comment.entity.Comment;

@Mapper
public interface CommentServiceMapper {
    CommentServiceMapper INSTANCE = Mappers.getMapper(CommentServiceMapper.class);

    @Mapping(source = "user.nickname", target = "nickname")
    CommentSaveRes toCommentSaveRes(Comment comment);

    @Mapping(source = "user.nickname", target = "nickname")
    CommentUpdateRes toCommentUpdateRes(Comment comment);
}
