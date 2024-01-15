package sparta.com.sappun.test;

import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;

public interface LikeBoardTest extends UserTest, BoardTest {

    LikeBoard TEST_LIKE_BOARD = LikeBoard.builder().board(TEST_BOARD).user(TEST_USER).build();
}
