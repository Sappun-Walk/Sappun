package sparta.com.sappun.domain.ReportComment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.ReportComment.entity.ReportComment;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.user.entity.User;

@RepositoryDefinition(domainClass = ReportComment.class, idClass = Long.class)
public interface ReportCommentRepository {

    ReportComment save(ReportComment reportComment);

    @Query(value = "select r.user FROM ReportComment r WHERE r.comment = :comment")
    List<User> selectUserByComment(Comment comment);

    @Query(value = "delete from ReportComment r where r.comment = :comment")
    void clearReportCommentByComment(Comment comment);
}
