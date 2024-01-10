package sparta.com.sappun.domain.LikeBoard.repository;

import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.LikeComment.entity.LikeComment;
import sparta.com.sappun.domain.board.entity.BoardLike;

@RepositoryDefinition(domainClass = BoardLike.class, idClass = Long.class)
public interface LikeBoardRepository {
    BoardLike save(BoardLike boardLike);
}
