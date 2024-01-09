package sparta.com.sappun.domain.like.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.security.UserDetailsImpl;

@Entity
@Getter
@NoArgsConstructor
public class LikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public LikePost(Post post, UserDetailsImpl userDetails) {
        this.post = post;
        this.user = userDetails.getUser();
    }
}
