package sparta.com.sappun.domain.likeComment.repository;

import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.likeComment.entity.LikeComment;
import sparta.com.sappun.domain.user.entity.User;

@RepositoryDefinition(domainClass = LikeComment.class, idClass = Long.class)
public interface LikeCommentRepository {
    LikeComment save(LikeComment commentLike);

    boolean existsLikeCommentByCommentAndUser(Comment comment, User user);

    void deleteLikeCommentByCommentAndUser(Comment comment, User user);
}
