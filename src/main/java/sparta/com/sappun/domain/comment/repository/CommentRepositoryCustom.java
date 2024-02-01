package sparta.com.sappun.domain.comment.repository;

import sparta.com.sappun.domain.user.entity.User;

public interface CommentRepositoryCustom {
    void deleteAllByUser(User user);
}
