package sparta.com.sappun.domain.board.repository;

import java.awt.*;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.board.entity.Board;

@RepositoryDefinition(domainClass = Board.class, idClass = long.class)
public interface BoardRepository extends BoardRepositoryCustom {
    Board save(Board board);

    void delete(Board board);
}
