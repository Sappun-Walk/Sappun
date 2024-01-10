package sparta.com.sappun.domain.LikeComment.service;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.yaml.snakeyaml.comments.CommentLine;
import sparta.com.sappun.domain.LikeComment.dto.response.LikeCommentSaveRes;
import sparta.com.sappun.domain.LikeComment.entity.LikeComment;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.user.service.UserServiceMapper;

public interface LikeCommentServiceMappper {
    LikeCommentServiceMappper INSTANCE = Mappers.getMapper(LikeCommentServiceMappper.class);


}
