package sparta.com.sappun.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentSaveRes saveComment(CommentSaveReq commentSaveReq) {
        // 보드 아이디 조회 로직 구현 필요

        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
                commentRepository.save(
                        Comment.builder()
                                .nickname(commentSaveReq.getNickname())
                                .content(commentSaveReq.getContent())
                                .fileUrl(commentSaveReq.getFileUrl())
                                .build()));
    }
}
