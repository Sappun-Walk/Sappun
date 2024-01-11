package sparta.com.sappun.domain.ReportBoard.repository;

import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.LikeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.ReportBoard.entity.ReportBoard;

import java.util.List;

@RepositoryDefinition(domainClass = ReportBoard.class, idClass = Long.class)
public interface ReportBoardRepository {
    ReportBoard save(ReportBoard reportBoard);
}
