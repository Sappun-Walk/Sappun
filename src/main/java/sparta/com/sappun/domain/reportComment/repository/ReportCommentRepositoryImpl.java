package sparta.com.sappun.domain.reportComment.repository;


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
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.entity.QComment;
import sparta.com.sappun.domain.reportComment.entity.QReportComment;
import sparta.com.sappun.domain.reportComment.entity.ReportComment;
import sparta.com.sappun.domain.user.entity.QUser;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.config.QuerydslConfig;

import java.util.List;

@RequiredArgsConstructor
@Import(QuerydslConfig.class)
public class ReportCommentRepositoryImpl implements ReportCommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QReportComment qReportComment = QReportComment.reportComment;
    private final QUser qUser = QUser.user;
    private final QComment qComment = QComment.comment;

    @Override
    public List<User> selectUserByComment(Comment comment) {
        return queryFactory
                .select(qReportComment.user)
                .from(qReportComment)
                .where(qReportComment.comment.eq(comment))
                .fetch();
    }

    @Override
    public List<ReportComment> selectReportCommentByUser(User user) {
        return queryFactory
                .selectFrom(qReportComment)
                .join(qReportComment.user, qUser).fetchJoin()
                .where(qReportComment.user.eq(user))
                .fetch();
    }

    @Override
    public void deleteAll(List<ReportComment> reportComments) {
        queryFactory
                .delete(qReportComment)
                .where(qReportComment.in(reportComments))
                .execute();
    }

    @Override
    public void clearReportCommentByComment(Comment comment) {
        queryFactory
                .delete(qReportComment)
                .where(qReportComment.comment.eq(comment))
                .execute();
    }

    @Override
    public Page<ReportComment> findAllFetchComment(Pageable pageable) {
        JPAQuery<ReportComment> query =
                queryFactory
                        .selectFrom(qReportComment)
                        .join(qReportComment.comment, qComment)
                        .fetchJoin();

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(qReportComment.getType(), qReportComment.getMetadata());
            query.orderBy(
                    new OrderSpecifier<>(
                            o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<ReportComment> reportComments = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        Long total =
                queryFactory
                        .select(qReportComment.count())
                        .from(qReportComment)
                        .fetchOne();

        long totalCount = (total != null) ? total : 0;

        return new PageImpl<>(reportComments, pageable, totalCount);
    }
}
