package sparta.com.sappun.domain.like.dto.response;


import lombok.Getter;
import sparta.com.sappun.domain.like.entity.Post;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto extends CommonResponseDto {
    private String title;
    private String nickname;
    private String content;
    private Long likes;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.content = post.getContent();
        this.likes = (long) post.getPostLikes().size();
    }
}