package sparta.com.sappun.domain.likeComment.repository;

import java.util.List;
import sparta.com.sappun.domain.likeComment.entity.LikeComment;
import sparta.com.sappun.domain.user.entity.User;

public interface LikeCommentRepositoryCustom {

    public List<LikeComment> selectLikeCommentByUser(User user);

    public void deleteAll(List<LikeComment> likeComments);
}
