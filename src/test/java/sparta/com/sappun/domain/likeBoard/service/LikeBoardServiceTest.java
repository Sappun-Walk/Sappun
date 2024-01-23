package sparta.com.sappun.domain.likeBoard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.likeBoard.dto.response.LikeBoardSaveRes;
import sparta.com.sappun.domain.likeBoard.repository.LikeBoardRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.LikeBoardTest;

@ExtendWith(MockitoExtension.class)
class LikeBoardServiceTest implements LikeBoardTest {
    @Mock UserRepository userRepository;
    @Mock BoardRepository boardRepository;
    @Mock LikeBoardRepository likeBoardRepository;

    @InjectMocks LikeBoardService likeBoardService;

    @Test
    @DisplayName("게시글 좋아요 저장 테스트 - 누르지 않은 경우")
    void clickLikeBoardTest() {

        // given
        Integer score = TEST_USER.getScore();
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(boardRepository.findById(any())).thenReturn(TEST_BOARD);
        when(likeBoardRepository.existsLikeBoardByBoardAndUser(any(), any())).thenReturn(false);

        // when
        LikeBoardSaveRes res = likeBoardService.clickLikeBoard(TEST_BOARD_ID, TEST_USER_ID);

        // then
        assertEquals(score + 10, TEST_USER.getScore());
        assertEquals(true, res.getIsLiked());
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        verify(boardRepository, times(1)).findById(TEST_BOARD_ID);
        verify(likeBoardRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("게시글 좋아요 저장 테스트 - 이미 누른 경우")
    void againClickLikeBoardTest() {

        // given
        Integer score = TEST_USER.getScore();
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(boardRepository.findById(any())).thenReturn(TEST_BOARD);
        when(likeBoardRepository.existsLikeBoardByBoardAndUser(any(), any())).thenReturn(true);

        // when
        LikeBoardSaveRes res = likeBoardService.clickLikeBoard(TEST_BOARD_ID, TEST_USER_ID);

        // then
        assertEquals(score - 10, TEST_USER.getScore());
        assertEquals(false, res.getIsLiked());
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        verify(boardRepository, times(1)).findById(TEST_BOARD_ID);
        verify(likeBoardRepository, times(1)).deleteLikeBoardByBoardAndUser(any(), any());
    }
}
