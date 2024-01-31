package sparta.com.sappun.domain.reportBoard.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.reportBoard.entity.ReportBoard;
import sparta.com.sappun.domain.user.entity.User;

public interface ReportBoardRepositoryCustom {

    List<User> selectUserByBoard(Board board);

    List<ReportBoard> selectReportBoardByUser(User user);

    void deleteAll(List<ReportBoard> reportBoards);

    void clearReportBoardByBoard(Board board);

    Page<ReportBoard> findAllFetchBoard(Pageable pageable);
}
