package sparta.com.sappun.domain.LikeBoard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparta.com.sappun.domain.LikeBoard.repository.LikeBoardRepository;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.LikeBoardTest;

@ExtendWith(MockitoExtension.class)
class LikeBoardServiceTest implements LikeBoardTest {
    @Mock UserRepository userRepository;
    @Mock BoardRepository boardRepository;
    @Mock LikeBoardRepository likeBoardRepository;

    @InjectMocks LikeBoardService likeBoardService;

    @Test
    @DisplayName("게시글 좋아요 저장 테스트")
    void clickLikeBoardTest() {

        // given
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(boardRepository.findById(any())).thenReturn(TEST_BOARD);
        when(likeBoardRepository.existsLikeBoardByBoardAndUser(any(), any())).thenReturn(false);
        // when
        likeBoardService.clickLikeBoard(TEST_BOARD_ID, TEST_USER_ID);

        // then
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        verify(boardRepository, times(1)).findById(TEST_BOARD_ID);
        verify(likeBoardRepository, times(1)).save(any());
    }
}
