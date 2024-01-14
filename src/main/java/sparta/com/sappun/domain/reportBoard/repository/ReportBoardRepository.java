package sparta.com.sappun.domain.reportBoard.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.reportBoard.entity.ReportBoard;
import sparta.com.sappun.domain.user.entity.User;

@RepositoryDefinition(domainClass = ReportBoard.class, idClass = Long.class)
public interface ReportBoardRepository {
    ReportBoard save(ReportBoard reportBoard);

    boolean existsReportBoardByBoardAndUser(Board board, User user);

    @Query(value = "select r.user FROM ReportBoard r WHERE r.board = :board")
    List<User> selectUserByBoard(Board board);

    @Modifying // select 외의 쿼리를 사용하기 위해서 필요함
    @Query(value = "delete from ReportBoard r where r.board = :board")
    void clearReportBoardByBoard(Board board);
}
