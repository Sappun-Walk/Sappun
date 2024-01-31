package sparta.com.sappun.domain.likeComment.repository;

import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.likeComment.entity.LikeComment;
import sparta.com.sappun.domain.user.entity.User;

@RepositoryDefinition(domainClass = LikeComment.class, idClass = Long.class)
public interface LikeCommentRepository extends LikeCommentRepositoryCustom {
    LikeComment save(LikeComment commentLike);

    boolean existsLikeCommentByCommentAndUser(Comment comment, User user);

    void deleteLikeCommentByCommentAndUser(Comment comment, User user);

    //    @Query(value = "select r FROM LikeComment r WHERE r.user = :user")
    //    List<LikeComment> selectLikeCommentByUser(User user);
    //
    //    @Modifying
    //    @Query(value = "delete from LikeComment lc where lc in :likeComments")
    //    void deleteAll(List<LikeComment> likeComments);
}
