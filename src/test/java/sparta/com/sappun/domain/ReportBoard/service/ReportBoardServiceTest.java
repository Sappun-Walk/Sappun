package sparta.com.sappun.domain.ReportBoard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparta.com.sappun.domain.ReportBoard.dto.request.ReportBoardReq;
import sparta.com.sappun.domain.ReportBoard.repository.ReportBoardRepository;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.ReportBoardTest;

@ExtendWith(MockitoExtension.class)
class ReportBoardServiceTest implements ReportBoardTest {

    @Mock private BoardRepository boardRepository;

    @Mock private UserRepository userRepository;

    @Mock private ReportBoardRepository reportBoardRepository;

    @InjectMocks private ReportBoardService reportBoardService;

    @Test
    @DisplayName("게시글 신고 테스트")
    // 더 직관적이게 clickReportBoardTest로 수정해주세요~
    void clickReportBoardTest() {
        // given
        // 이 두 줄은 //given 밑으로 옮겨주세요~
        ReportBoardReq req = ReportBoardReq.builder().reason(TEST_BOARD_REPORT_REASON).build();
        req.setUserId(TEST_USER_ID);
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(boardRepository.findById(any())).thenReturn(TEST_BOARD);
        when(reportBoardRepository.existsReportBoardByBoardAndUser(any(), any())).thenReturn(false);
        // when
        reportBoardService.clickReportBoard(TEST_BOARD_ID, req);
        // then
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        verify(boardRepository, times(1)).findById(TEST_BOARD_ID);
        verify(reportBoardRepository, times(1)).existsReportBoardByBoardAndUser(any(), any());
        verify(reportBoardRepository, times(1)).save(any());
    }
}
