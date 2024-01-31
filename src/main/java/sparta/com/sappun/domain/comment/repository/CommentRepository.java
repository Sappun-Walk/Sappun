package sparta.com.sappun.domain.comment.repository;

import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.comment.entity.Comment;

@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRepository extends CommentRepositoryCustom {
    Comment save(Comment comment);

    Comment findById(Long commentId);

    void delete(Comment comment);

    //    @Modifying
    //    @Query(value = "delete from Comment c where c.user = :user")
    //    void deleteAllByUser(User user);
}
