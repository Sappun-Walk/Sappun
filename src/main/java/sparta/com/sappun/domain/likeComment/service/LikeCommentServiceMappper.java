package sparta.com.sappun.domain.likeComment.service;

import org.mapstruct.factory.Mappers;

public interface LikeCommentServiceMappper {
    LikeCommentServiceMappper INSTANCE = Mappers.getMapper(LikeCommentServiceMappper.class);
}
