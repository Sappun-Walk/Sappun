package sparta.com.sappun.domain.likeBoard.repository;

import java.util.List;
import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.user.entity.User;

public interface LikeRepositoryCustom {
    List<LikeBoard> selectLikeBoardByUser(User user);

    void deleteAll(List<LikeBoard> likeBoards);
}
