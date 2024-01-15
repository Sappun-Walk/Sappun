package sparta.com.sappun.domain.board.repository;

import java.awt.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.RegionEnum;

@RepositoryDefinition(domainClass = Board.class, idClass = long.class)
public interface BoardRepository {

    Board findById(Long boardId);

    Page<Board> findAllByRegion(RegionEnum region, Pageable pageable);

    List<Board> findTop3ByOrderByLikeCountDesc();

    Board save(Board board);

    void delete(Board board);
}
