package sparta.com.sappun.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.com.sappun.domain.like.entity.LikePost;
import sparta.com.sappun.domain.like.entity.Post;
import sparta.com.sappun.domain.user.entity.User;

import java.util.List;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    List<LikePost> findAllByPost(Post post);

    LikePost findByPostAndUser(Post post, User user);
}
