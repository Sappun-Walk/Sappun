package sparta.com.sappun.domain.reportBoard.repository;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.QBoard;
import sparta.com.sappun.domain.reportBoard.entity.QReportBoard;
import sparta.com.sappun.domain.reportBoard.entity.ReportBoard;
import sparta.com.sappun.domain.user.entity.QUser;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.config.QuerydslConfig;

import java.util.List;

@RequiredArgsConstructor
@Import(QuerydslConfig.class)
public class ReportBoardRepositoryImpl implements ReportBoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QReportBoard qReportBoard = QReportBoard.reportBoard;
    private final QBoard qBoard = QBoard.board;
    private final QUser qUser = QUser.user;

    @Override
    public List<User> selectUserByBoard(Board board) {
        return queryFactory
                .select(qReportBoard.user)
                .from(qReportBoard)
                .where(qReportBoard.board.eq(board))
                .fetch();
    }

    @Override
    public List<ReportBoard> selectReportBoardByUser(User user) {
        return queryFactory
                .selectFrom(qReportBoard)
                .join(qReportBoard.user, qUser).fetchJoin()
                .where(qReportBoard.user.eq(user))
                .fetch();
    }

    @Override
    public void deleteAll(List<ReportBoard> reportBoards) {
        queryFactory
                .delete(qReportBoard)
                .where(qReportBoard.in(reportBoards))
                .execute();
    }

    @Override
    public void clearReportBoardByBoard(Board board) {
        queryFactory
                .delete(qReportBoard)
                .where(qReportBoard.board.eq(board))
                .execute();
    }

    @Override
    public Page<ReportBoard> findAllFetchBoard(Pageable pageable) {
        JPAQuery<ReportBoard> query =
                queryFactory
                        .selectFrom(qReportBoard)
                        .join(qReportBoard.board, qBoard)
                        .fetchJoin();

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(qReportBoard.getType(), qReportBoard.getMetadata());
            query.orderBy(
                    new OrderSpecifier<>(
                            o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<ReportBoard> reportBoards = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        Long total =
                queryFactory
                        .select(qReportBoard.count())
                        .from(qReportBoard)
                        .fetchOne();

        long totalCount = (total != null) ? total : 0;

        return new PageImpl<>(reportBoards, pageable, totalCount);
    }
}
