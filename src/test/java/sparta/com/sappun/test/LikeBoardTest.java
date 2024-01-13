package sparta.com.sappun.test;

import sparta.com.sappun.domain.LikeBoard.entity.LikeBoard;

// Board 관련 내용은 BoardTest를 만들어서 그 안에 넣어주신 후에
public interface LikeBoardTest extends UserTest, BoardTest {

    LikeBoard TEST_LIKE_BOARD = LikeBoard.builder().board(TEST_BOARD).user(TEST_USER).build();
}
