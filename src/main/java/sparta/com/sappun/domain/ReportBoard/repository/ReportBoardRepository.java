package sparta.com.sappun.domain.ReportBoard.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.ReportBoard.entity.ReportBoard;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.user.entity.User;

@RepositoryDefinition(domainClass = ReportBoard.class, idClass = Long.class)
public interface ReportBoardRepository {
    ReportBoard save(ReportBoard reportBoard);

    @Query(value = "select r.user FROM ReportBoard r WHERE r.board = :board")
    List<User> selectUserByBoard(Board board);

    @Query(value = "delete from ReportBoard r where r.board = :board")
    void clearReportBoardByBoard(Board board);
}
