package sparta.com.sappun.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.comment.entity.QComment;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.config.QuerydslConfig;

@RequiredArgsConstructor
@Import(QuerydslConfig.class)
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QComment qComment = QComment.comment;

    @Override
    @Transactional
    public void deleteAllByUser(User user) {
        queryFactory.delete(qComment).where(qComment.user.eq(user)).execute();
    }
}
