package sparta.com.sappun.domain.board.repository;

import java.util.List;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@RepositoryDefinition(domainClass = Board.class, idClass = long.class)
public interface BoardRepository {

    Board findById(Long boardId);

    List<Board> findAllByRegion(RegionEnum region);

    List<Board> findTop3ByOrderByBoardLikesDesc();

    Board save(Board board);

    void delete(Board board);
}
