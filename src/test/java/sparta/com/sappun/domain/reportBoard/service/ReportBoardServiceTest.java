package sparta.com.sappun.domain.reportBoard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static sparta.com.sappun.global.response.ResultCode.DUPLICATED_REPORT_BOARD;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.reportBoard.dto.request.ReportBoardReq;
import sparta.com.sappun.domain.reportBoard.repository.ReportBoardRepository;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.exception.GlobalException;
import sparta.com.sappun.test.ReportBoardTest;

@ExtendWith(MockitoExtension.class)
class ReportBoardServiceTest implements ReportBoardTest {

    @Mock private BoardRepository boardRepository;

    @Mock private UserRepository userRepository;

    @Mock private ReportBoardRepository reportBoardRepository;

    @InjectMocks private ReportBoardService reportBoardService;

    @Test
    @DisplayName("게시글 신고 테스트")
    void clickReportBoardTest() {
        // given
        ReportBoardReq req = ReportBoardReq.builder().reason(TEST_BOARD_REPORT_REASON).build();
        req.setUserId(TEST_USER_ID);
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(boardRepository.findById(any())).thenReturn(TEST_BOARD);
        when(reportBoardRepository.existsReportBoardByBoardAndUser(any(), any())).thenReturn(false);
        Integer score = TEST_BOARD.getUser().getScore();

        // when
        reportBoardService.clickReportBoard(TEST_BOARD_ID, req);

        // then
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        verify(boardRepository, times(1)).findById(TEST_BOARD_ID);
        verify(reportBoardRepository, times(1)).existsReportBoardByBoardAndUser(any(), any());
        verify(reportBoardRepository, times(1)).save(any());
        assertEquals(score - 50, TEST_BOARD.getUser().getScore());
    }

    @Test
    @DisplayName("게시글 신고 테스트 - 이미 신고한 경우")
    void duplicatedClickReportBoardTest() {
        // given
        ReportBoardReq req = ReportBoardReq.builder().reason(TEST_BOARD_REPORT_REASON).build();
        req.setUserId(TEST_USER_ID);
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(boardRepository.findById(any())).thenReturn(TEST_BOARD);
        when(reportBoardRepository.existsReportBoardByBoardAndUser(any(), any())).thenReturn(true);

        // when
        GlobalException exception =
                assertThrows(
                        GlobalException.class,
                        () -> {
                            reportBoardService.clickReportBoard(TEST_BOARD_ID, req);
                        });

        // then
        assertEquals(DUPLICATED_REPORT_BOARD.getMessage(), exception.getResultCode().getMessage());
    }

    @Test
    @DisplayName("게시글 신고 삭제 테스트")
    void deleteReportBoardTest() {
        // given
        User user1 =
                User.builder()
                        .username("username1")
                        .nickname("nickname1")
                        .password("password1")
                        .email("email1@dd.d")
                        .role(Role.USER)
                        .score(0)
                        .build();

        User user2 =
                User.builder()
                        .username("username2")
                        .nickname("nickname2")
                        .password("password2")
                        .email("email2@dd.d")
                        .role(Role.USER)
                        .score(0)
                        .build();

        when(boardRepository.findById(any())).thenReturn(TEST_BOARD);
        Integer boardAuthorScore = TEST_BOARD.getUser().getScore();
        List<User> reporters = List.of(user1, user2);
        when(reportBoardRepository.selectUserByBoard(any())).thenReturn(reporters);

        // when
        reportBoardService.deleteReportBoard(TEST_BOARD_ID);

        // then
        verify(reportBoardRepository).clearReportBoardByBoard(TEST_BOARD);
        assertEquals(-50, user1.getScore());
        assertEquals(boardAuthorScore + 100, TEST_BOARD.getUser().getScore());
    }
}
