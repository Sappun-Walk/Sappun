package sparta.com.sappun.domain.like.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sparta.com.sappun.domain.like.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}
