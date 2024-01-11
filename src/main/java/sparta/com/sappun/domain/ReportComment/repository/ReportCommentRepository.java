package sparta.com.sappun.domain.ReportComment.repository;

import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.ReportComment.entity.ReportComment;

@RepositoryDefinition(domainClass = ReportComment.class, idClass = Long.class)
public interface ReportCommentRepository {
    ReportComment save(ReportComment reportComment);
}
