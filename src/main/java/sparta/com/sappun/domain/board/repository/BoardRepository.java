package sparta.com.sappun.domain.board.repository;

import io.lettuce.core.dynamic.annotation.Param;
import java.awt.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.RegionEnum;
import sparta.com.sappun.domain.user.entity.User;

@RepositoryDefinition(domainClass = Board.class, idClass = long.class)
public interface BoardRepository {

    Board findById(Long boardId);

    Page<Board> findAllByReportCountLessThanAndRegion(
            int reportCount, RegionEnum region, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.user.id = :userId")
    Page<Board> findAllUserBoardByUserId(@Param("userId") Long userId, Pageable pageable);

    Page<Board> findAll(Pageable pageable);

    List<Board> findTop3ByReportCountLessThanOrderByLikeCountDesc(int reportCount);

    Board save(Board board);

    void delete(Board board);

    @Modifying
    @Query(value = "delete from Board b where b.user = :user")
    void deleteAllByUser(User user);
}
