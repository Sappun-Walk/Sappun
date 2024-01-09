package sparta.com.sappun.domain.like.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.like.dto.request.PostRequestDto;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.global.security.UserDetailsImpl;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<LikePost> postLikes;

    public Post(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.user = userDetails.getUser();
    }
}
