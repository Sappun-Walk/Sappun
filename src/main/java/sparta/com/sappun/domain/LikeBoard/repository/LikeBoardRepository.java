package sparta.com.sappun.domain.LikeBoard.repository;

import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.LikeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.user.entity.User;

@RepositoryDefinition(domainClass = LikeBoard.class, idClass = Long.class)
public interface LikeBoardRepository {
    LikeBoard save(LikeBoard boardLike);

    boolean existsLikeBoardByBoardAndUser(Board board, User user);

    void deleteLikeBoardByBoardAndUser(Board board, User user);
}
