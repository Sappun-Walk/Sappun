package sparta.com.sappun.domain.likeBoard.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.likeBoard.entity.QLikeBoard;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.config.QuerydslConfig;

@Slf4j
@RequiredArgsConstructor
@Import(QuerydslConfig.class)
public class LikeRepositoryCustomImpl implements LikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QLikeBoard qLikeBoard = QLikeBoard.likeBoard;

    @Override
    public List<LikeBoard> selectLikeBoardByUser(User user) {
        return queryFactory.selectFrom(qLikeBoard).where(qLikeBoard.user.eq(user)).fetch();
    }

    @Override
    public void deleteAll(List<LikeBoard> likeBoards) {
        queryFactory.delete(qLikeBoard).where(qLikeBoard.in(likeBoards)).execute();
    }
}
