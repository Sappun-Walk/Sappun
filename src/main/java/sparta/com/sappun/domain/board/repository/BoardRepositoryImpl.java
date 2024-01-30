package sparta.com.sappun.domain.board.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.QBoard;
import sparta.com.sappun.domain.board.entity.QImage;
import sparta.com.sappun.domain.board.entity.RegionEnum;
import sparta.com.sappun.domain.user.entity.QUser;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.config.QuerydslConfig;

@RequiredArgsConstructor
@Import(QuerydslConfig.class)
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QBoard qBoard = QBoard.board;
    private final QUser qUser = QUser.user;
    private final QImage qImage = QImage.image;

    @Override
    public Board findById(Long boardId) {
        return queryFactory
                .selectFrom(qBoard)
                .join(qBoard.user, qUser)
                .fetchJoin()
                .leftJoin(qBoard.images, qImage)
                .fetchJoin()
                .where(qBoard.id.eq(boardId))
                .fetchOne();
    }

    @Override
    @Transactional
    public void deleteAllByUser(User user) {
        queryFactory.delete(qBoard).where(qBoard.user.eq(user)).execute();
    }

    @Override
    public List<Board> findTop3ByReportCountLessThanOrderByLikeCountDesc(int maxReportCount) {
        return queryFactory
                .selectFrom(qBoard)
                .join(qBoard.user, qUser)
                .fetchJoin()
                .where(qBoard.reportCount.lt(maxReportCount))
                .orderBy(qBoard.likeCount.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public Page<Board> findAllByUserId(Long userId, Pageable pageable) {
        JPAQuery<Board> query =
                queryFactory
                        .selectFrom(qBoard)
                        .join(qBoard.user, qUser)
                        .fetchJoin()
                        .where(qBoard.user.id.eq(userId));

        // Pageable의 Sort 정보를 사용하여 정렬 조건 추가
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(qBoard.getType(), qBoard.getMetadata());
            query.orderBy(
                    new OrderSpecifier<>(
                            o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<Board> boards = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        Long total =
                queryFactory
                        .select(qBoard.count())
                        .from(qBoard)
                        .where(qBoard.user.id.eq(userId))
                        .fetchOne();

        // null 체크 후 기본값 제공
        long totalCount = (total != null) ? total : 0;

        return new PageImpl<>(boards, pageable, totalCount);
    }

    @Override
    public Page<Board> findAllByReportCountLessThanAndRegion(
            int reportCount, RegionEnum region, Pageable pageable) {
        JPAQuery<Board> query =
                queryFactory
                        .selectFrom(qBoard)
                        .join(qBoard.user, qUser)
                        .fetchJoin()
                        .where(qBoard.region.eq(region))
                        .where(qBoard.reportCount.lt(reportCount));

        // Pageable의 Sort 정보를 사용하여 정렬 조건 추가
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(qBoard.getType(), qBoard.getMetadata());
            query.orderBy(
                    new OrderSpecifier<>(
                            o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<Board> boards = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        Long total =
                queryFactory
                        .select(qBoard.count())
                        .from(qBoard)
                        .where(qBoard.region.eq(region))
                        .where(qBoard.reportCount.lt(reportCount))
                        .fetchOne();

        // null 체크 후 기본값 제공
        long totalCount = (total != null) ? total : 0;

        return new PageImpl<>(boards, pageable, totalCount);
    }

    @Override
    public Page<Board> findAllByReportCountLessThan(int reportCount, Pageable pageable) {
        JPAQuery<Board> query =
                queryFactory
                        .selectFrom(qBoard)
                        .join(qBoard.user, qUser)
                        .fetchJoin()
                        .where(qBoard.reportCount.lt(reportCount));

        // Pageable의 Sort 정보를 사용하여 정렬 조건 추가
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(qBoard.getType(), qBoard.getMetadata());
            query.orderBy(
                    new OrderSpecifier<>(
                            o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<Board> boards = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        Long total =
                queryFactory
                        .select(qBoard.count())
                        .from(qBoard)
                        .where(qBoard.reportCount.lt(reportCount))
                        .fetchOne();

        // null 체크 후 기본값 제공
        long totalCount = (total != null) ? total : 0;

        return new PageImpl<>(boards, pageable, totalCount);
    }
}
