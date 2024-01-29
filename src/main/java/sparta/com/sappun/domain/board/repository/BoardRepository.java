package sparta.com.sappun.domain.board.repository;

import java.awt.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@RepositoryDefinition(domainClass = Board.class, idClass = long.class)
public interface BoardRepository extends BoardRepositoryCustom {

    Board findById(Long boardId);

    Board save(Board board);

    void delete(Board board);

    Page<Board> findAllByReportCountLessThanAndRegion(
            int reportCount, RegionEnum region, Pageable pageable);

    Page<Board> findAllByReportCountLessThan(int reportCount, Pageable pageable);
}
