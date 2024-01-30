package sparta.com.sappun.domain.likeBoard.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import sparta.com.sappun.domain.board.entity.QBoard;
import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.likeBoard.entity.QLikeBoard;
import sparta.com.sappun.domain.user.entity.QUser;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.config.QuerydslConfig;

@Slf4j
@RequiredArgsConstructor
@Import(QuerydslConfig.class)
public class LikeRepositoryCustomImpl implements LikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QLikeBoard qLikeBoard = QLikeBoard.likeBoard;
    private final QBoard qBoard = QBoard.board;
    private final QUser qUser = QUser.user;

    @Override
    public List<LikeBoard> selectLikeBoardByUser(User user) {
        return queryFactory.selectFrom(qLikeBoard).where(qLikeBoard.user.eq(user)).fetch();
    }

    @Override
    public void deleteAll(List<LikeBoard> likeBoards) {
        queryFactory.delete(qLikeBoard).where(qLikeBoard.in(likeBoards)).execute();
    }

    @Override
    public Page<LikeBoard> findAllByUser(User user, Pageable pageable) {
        JPAQuery<LikeBoard> query =
                queryFactory
                        .selectFrom(qLikeBoard)
                        .join(qLikeBoard.board, qBoard)
                        .fetchJoin()
                        .join(qLikeBoard.user, qUser)
                        .fetchJoin()
                        .where(qLikeBoard.user.eq(user));

        // Pageable의 Sort 정보를 사용하여 정렬 조건 추가
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(qLikeBoard.getType(), qLikeBoard.getMetadata());
            query.orderBy(
                    new OrderSpecifier<>(
                            o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<LikeBoard> likeBoards =
                query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        Long total =
                queryFactory
                        .select(qLikeBoard.count())
                        .from(qLikeBoard)
                        .where(qLikeBoard.user.eq(user))
                        .fetchOne();

        // null 체크 후 기본값 제공
        long totalCount = (total != null) ? total : 0;

        return new PageImpl<>(likeBoards, pageable, totalCount);
    }
}
