package sparta.com.sappun.domain.reportComment.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.reportComment.entity.ReportComment;
import sparta.com.sappun.domain.user.entity.User;

public interface ReportCommentRepositoryCustom {

    List<User> selectUserByComment(Comment comment);

    List<ReportComment> selectReportCommentByUser(User user);

    void deleteAll(List<ReportComment> reportComments);

    void clearReportCommentByComment(Comment comment);

    Page<ReportComment> findAllFetchComment(Pageable pageable);
}
