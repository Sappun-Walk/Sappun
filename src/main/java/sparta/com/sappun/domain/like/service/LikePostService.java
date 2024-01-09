package sparta.com.sappun.domain.like.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.com.sappun.domain.like.entity.LikePost;
import sparta.com.sappun.domain.like.entity.Post;
import sparta.com.sappun.domain.like.repository.LikePostRepository;
import sparta.com.sappun.domain.like.repository.PostRepository;
import sparta.com.sappun.global.security.UserDetailsImpl;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LikePostService {

    private final LikePostRepository postLikesRepository;
    private final PostRepository postRepository;


    public boolean toggleLike(Long postId, UserDetailsImpl userDetails) {
        // 해당 id의 게시물이 존재하는지 검증
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 id의 게시물이 없습니다."));

        // 게시글 작성자 본인인지 검증
        if (Objects.equals(post.getUser().getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("본인의 게시물에 좋아요를 누를 수 없습니다.");
        }

        // 해당 게시물과 사용자 정보를 가지고 있는 좋아요 객체 생성
        LikePost likes = new LikePost(post, userDetails);

        // 해당 게시물에 대한 모든 좋아요 정보를 조회
        List<LikePost> postLikesList = postLikesRepository.findAllByPost(post);

        // 조회된 좋아요 목록을 순회
        for (LikePost postLikes : postLikesList) {
            // 현재 사용자가 이미 해당 게시물에 좋아요를 눌렀는지 확인
            if (Objects.equals(postLikes.getUser().getId(), userDetails.getUser().getId())) {
                // 이미 좋아요를 눌렀다면 해당 좋아요 정보를 찾은 후 삭제
                LikePost alreadyLike = postLikesRepository.findByPostAndUser(likes.getPost(), likes.getUser());
                postLikesRepository.delete(alreadyLike);
                return false;
            }
        }
        // 사용자가 아직 좋아요를 누르지 않았다면 좋아요 정보 추가
        postLikesRepository.save(likes);
        return true;
    }
}
