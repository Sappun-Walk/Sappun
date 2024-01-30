package sparta.com.sappun.domain.likeBoard.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.user.entity.User;

public interface LikeRepositoryCustom {
    List<LikeBoard> selectLikeBoardByUser(User user);

    void deleteAll(List<LikeBoard> likeBoards);

    Page<LikeBoard> findAllByUser(User user, Pageable pageable);
}
