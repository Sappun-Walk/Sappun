package sparta.com.sappun.domain.comment.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.entity.Comment;

@Mapper
public interface CommentServiceMapper {
    CommentServiceMapper INSTANCE = Mappers.getMapper(CommentServiceMapper.class);

    CommentSaveRes toCommentSaveRes(Comment comment);
}
