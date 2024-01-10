package sparta.com.sappun.domain.LikeComment.repository;


import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.LikeComment.entity.LikeComment;


@RepositoryDefinition(domainClass = LikeComment.class, idClass = Long.class)
public interface LikeCommentRepository {
    LikeComment save(LikeComment commentLike);
}