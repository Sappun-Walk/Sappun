package sparta.com.sappun.domain.likeComment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import sparta.com.sappun.domain.likeComment.entity.LikeComment;
import sparta.com.sappun.domain.likeComment.entity.QLikeComment;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.config.QuerydslConfig;

@RequiredArgsConstructor
@Import(QuerydslConfig.class)
public class LikeCommentRepositoryImpl implements LikeCommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QLikeComment qLikeComment = QLikeComment.likeComment;

    @Override
    public void deleteAll(List<LikeComment> likeComments) {
        queryFactory.delete(qLikeComment).where(qLikeComment.in(likeComments)).execute();
    }

    @Override
    public List<LikeComment> selectLikeCommentByUser(User user) {
        return queryFactory.selectFrom(qLikeComment).where(qLikeComment.user.eq(user)).fetch();
    }
}
