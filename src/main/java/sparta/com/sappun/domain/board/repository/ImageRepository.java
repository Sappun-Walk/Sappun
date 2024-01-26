package sparta.com.sappun.domain.board.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.Image;

@RepositoryDefinition(domainClass = Image.class, idClass = Long.class)
public interface ImageRepository {

    @Modifying
    @Query(value = "delete from Image i where i.board = :board")
    void deleteAllByBoard(Board board);
}
