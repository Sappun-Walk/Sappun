package sparta.com.sappun.domain.reportComment.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.reportComment.entity.ReportComment;
import sparta.com.sappun.domain.user.entity.User;

@RepositoryDefinition(domainClass = ReportComment.class, idClass = Long.class)
public interface ReportCommentRepository extends ReportCommentRepositoryCustom{

    ReportComment save(ReportComment reportComment);

    boolean existsReportCommentByCommentAndUser(Comment comment, User user);

//    @Query(value = "select r.user FROM ReportComment r WHERE r.comment = :comment")
//    List<User> selectUserByComment(Comment comment);
//
//    @Query(value = "select r FROM ReportComment r WHERE r.user = :user")
//    List<ReportComment> selectReportCommentByUser(User user);
//
//    @Modifying
//    @Query(value = "delete from ReportComment rc where rc in :reportComments")
//    void deleteAll(List<ReportComment> reportComments);
//
//    @Modifying // select 외의 쿼리를 사용하기 위해서 필요함
//    @Query(value = "delete from ReportComment r where r.comment = :comment")
//    void clearReportCommentByComment(Comment comment);
//
//    @Query("SELECT rc FROM ReportComment rc JOIN FETCH rc.comment")
//    Page<ReportComment> findAllFetchComment(Pageable pageable);
}
