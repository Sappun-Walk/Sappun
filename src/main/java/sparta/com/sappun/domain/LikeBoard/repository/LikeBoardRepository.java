package sparta.com.sappun.domain.LikeBoard.repository;

import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.LikeBoard.entity.LikeBoard;

@RepositoryDefinition(domainClass = LikeBoard.class, idClass = Long.class)
public interface LikeBoardRepository {
    LikeBoard save(LikeBoard boardLike);
}
