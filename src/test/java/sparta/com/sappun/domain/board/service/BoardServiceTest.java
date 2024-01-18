package sparta.com.sappun.domain.board.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.infra.s3.S3Util;
import sparta.com.sappun.test.BoardTest;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest implements BoardTest {
    @Mock BoardRepository boardRepository;
    @Mock UserRepository userRepository;

    @Mock S3Util s3Util;

    @InjectMocks BoardService boardService;

    @Captor ArgumentCaptor<Board> argumentCaptor;

    static MultipartFile multipartFile;

    @BeforeAll
    static void setUpProfile() throws IOException {
        String imageUrl = "static/images/image1.jpg";
        Resource fileResource = new ClassPathResource(imageUrl);

        multipartFile =
                new MockMultipartFile(
                        "image", fileResource.getFilename(), IMAGE_JPEG_VALUE, fileResource.getInputStream());
    }

    @Test
    @DisplayName("getBoard 테스트")
    void getBoardTest() {
        // given
        when(boardRepository.findById(TEST_BOARD_ID)).thenReturn(TEST_BOARD);
        ReflectionTestUtils.setField(TEST_BOARD, "id", TEST_BOARD_ID);

        // when
        BoardGetRes res = boardService.getBoard(TEST_BOARD_ID);

        // then
        assertEquals(TEST_BOARD_ID, res.getId());
        assertEquals(TEST_BOARD_ID, res.getId());
        assertEquals(TEST_BOARD_TITLE, res.getTitle());
        assertEquals(TEST_BOARD_CONTENT, res.getContent());
        assertEquals(TEST_BOARD_URL, res.getFileURL());
        assertEquals(TEST_DEPARTURE, res.getDeparture());
        assertEquals(TEST_DESTINATION, res.getDestination());
        assertEquals(TEST_STOPOVER, res.getStopover());
        assertEquals(TEST_REGION1, res.getRegion());
        assertEquals(TEST_LIKECOUNT, res.getLikeCount());
    }

    @Test
    @DisplayName("saveBoard 테스트")
    void saveBoardTest() {
        //        // given
        //        BoardSaveReq req =
        //                BoardSaveReq.builder()
        //                        .title(TEST_BOARD_TITLE)
        //                        .content(TEST_BOARD_CONTENT)
        //                        .departure(TEST_DEPARTURE)
        //                        .destination(TEST_DESTINATION)
        //                        .stopover(TEST_STOPOVER)
        //                        .region(TEST_REGION1)
        //                        .likeCount(TEST_LIKECOUNT)
        //                        .reportCount(TEST_REPORTCOUNT)
        //                        .build();
        //        req.setUserId(TEST_USER_ID);
        //
        //        // when
        //        when(boardRepository.findById(any())).thenReturn(TEST_BOARD);
        //        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_BOARD_URL);
        //
        //        boardService.saveBoard(req, multipartFile);
        //
        //        // then
        //        verify(boardRepository).save(argumentCaptor.capture());
        //        assertEquals(TEST_BOARD_ID, argumentCaptor.getValue().getId());
        //        assertEquals(TEST_BOARD_URL, argumentCaptor.getValue().getFileURL());
        //        assertEquals(TEST_BOARD_TITLE, argumentCaptor.getValue().getTitle());
        //        assertEquals(TEST_BOARD_CONTENT, argumentCaptor.getValue().getContent());
        //        assertEquals(TEST_DEPARTURE, argumentCaptor.getValue().getDeparture());
        //        assertEquals(TEST_DESTINATION, argumentCaptor.getValue().getDestination());
        //        assertEquals(TEST_STOPOVER, argumentCaptor.getValue().getStopover());
        //        assertEquals(TEST_REGION1, argumentCaptor.getValue().getRegion());
        //        assertEquals(TEST_LIKECOUNT, argumentCaptor.getValue().getReportCount());
        //        assertEquals(TEST_REPORTCOUNT, argumentCaptor.getValue().getLikeCount());
    }
}
