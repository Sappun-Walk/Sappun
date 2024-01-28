package sparta.com.sappun.domain.likeBoard.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.user.entity.User;

@RepositoryDefinition(domainClass = LikeBoard.class, idClass = Long.class)
public interface LikeBoardRepository {
    LikeBoard save(LikeBoard boardLike);

    List<LikeBoard> findByUser(User user);

    boolean existsLikeBoardByBoardAndUser(Board board, User user);

    void deleteLikeBoardByBoardAndUser(Board board, User user);

    @Query(value = "select r FROM LikeBoard r WHERE r.user = :user")
    List<LikeBoard> selectLikeBoardByUser(User user);

    @Modifying
    @Query(value = "delete from LikeBoard lb where lb in :likeBoards")
    void deleteAll(List<LikeBoard> likeBoards);

    @Query("SELECT rb FROM LikeBoard  rb JOIN FETCH rb.board")
    Page<LikeBoard> findAllFetchBoard(Pageable pageable);
}
