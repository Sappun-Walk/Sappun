package sparta.com.sappun.domain.comment.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.user.entity.User;

@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRepository {
    Comment save(Comment comment);

    Comment findById(Long commentId);

    void delete(Comment comment);

    @Modifying
    @Query(value = "delete from Comment c where c.user = :user")
    void deleteAllByUser(User user);
}
