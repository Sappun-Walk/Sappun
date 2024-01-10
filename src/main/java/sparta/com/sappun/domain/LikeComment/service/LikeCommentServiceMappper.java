package sparta.com.sappun.domain.LikeComment.service;

import org.mapstruct.factory.Mappers;

public interface LikeCommentServiceMappper {
    LikeCommentServiceMappper INSTANCE = Mappers.getMapper(LikeCommentServiceMappper.class);
}
