package sparta.com.sappun.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.QBoard;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.config.QuerydslConfig;

@RequiredArgsConstructor
@Import(QuerydslConfig.class)
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QBoard qBoard = QBoard.board;

    @Override
    @Transactional
    public void deleteAllByUser(User user) {
        queryFactory.delete(qBoard).where(qBoard.user.eq(user)).execute();
    }

    @Override
    public List<Board> findTop3ByReportCountLessThanOrderByLikeCountDesc(int maxReportCount) {
        return queryFactory
                .selectFrom(qBoard)
                .where(qBoard.reportCount.lt(maxReportCount))
                .orderBy(qBoard.likeCount.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public Page<Board> findAllByUserId(Long userId, Pageable pageable) {
        List<Board> boards =
                queryFactory
                        .selectFrom(qBoard)
                        .where(qBoard.user.id.eq(userId))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        Long total =
                queryFactory
                        .select(qBoard.count())
                        .from(qBoard)
                        .where(qBoard.user.id.eq(userId))
                        .fetchOne();

        // null 체크 후 기본값 제공
        long totalCount = total != null ? total : 0;

        return new PageImpl<>(boards, pageable, totalCount);
    }
}
