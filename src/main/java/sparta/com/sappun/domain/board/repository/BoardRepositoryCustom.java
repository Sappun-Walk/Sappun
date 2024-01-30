package sparta.com.sappun.domain.board.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.RegionEnum;
import sparta.com.sappun.domain.user.entity.User;

public interface BoardRepositoryCustom {
    Board findById(Long boardId);

    void deleteAllByUser(User user);

    List<Board> findTop3ByReportCountLessThanOrderByLikeCountDesc(int maxReportCount);

    Page<Board> findAllByUserId(Long userId, Pageable pageable);

    Page<Board> findAllByReportCountLessThanAndRegion(
            int reportCount, RegionEnum region, Pageable pageable);

    Page<Board> findAllByReportCountLessThan(int reportCount, Pageable pageable);
}
