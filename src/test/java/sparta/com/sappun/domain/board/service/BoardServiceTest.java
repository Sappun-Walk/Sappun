package sparta.com.sappun.domain.board.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import sparta.com.sappun.domain.board.dto.request.BoardSaveReq;
import sparta.com.sappun.domain.board.dto.request.BoardUpdateReq;
import sparta.com.sappun.domain.board.dto.response.BoardBestListGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardToListGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardToReportGetRes;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.board.repository.ImageRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.infra.s3.S3Util;
import sparta.com.sappun.infra.s3.S3Util.FilePath;
import sparta.com.sappun.test.BoardTest;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest implements BoardTest {
    @Mock BoardRepository boardRepository;
    @Mock UserRepository userRepository;
    @Mock ImageRepository imageRepository;

    @Mock S3Util s3Util;

    @InjectMocks BoardService boardService;

    @Captor ArgumentCaptor<Board> argumentCaptor;

    @PersistenceContext EntityManager em;

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
        assertEquals(TEST_MAP_IMAGE, res.getFileURL());
        assertEquals(TEST_DEPARTURE, res.getDeparture());
        assertEquals(TEST_DESTINATION, res.getDestination());
        assertEquals(TEST_STOPOVER, res.getStopover());
        assertEquals(TEST_REGION1, res.getRegion());
        assertEquals(TEST_LIKE_COUNT, res.getLikeCount());
    }

    @Test
    @DisplayName("getBoardList 테스트")
    void getBoardListTest() {
        // given
        int page = 0;
        int size = 8;
        String sortBy = "createdAt";
        boolean isAsc = false;
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Board> mockBoardList = List.of(TEST_BOARD1, TEST_BOARD2);
        Page<Board> mockPage = new PageImpl<>(mockBoardList, pageable, mockBoardList.size());

        when(boardRepository.findAllByReportCountLessThanAndRegion(5, TEST_REGION1, pageable))
                .thenReturn(mockPage);

        // when
        Page<BoardToListGetRes> res =
                boardService.getBoardList(TEST_REGION1, page, size, sortBy, isAsc);

        // then
        assertEquals(1, res.getTotalPages());
        assertEquals(2, res.getTotalElements());
    }

    @Test
    @DisplayName("getBoardAllList 테스트")
    void getBoardAllListTest() {
        // given
        int page = 0;
        int size = 8;
        String sortBy = "createdAt";
        boolean isAsc = false;
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Board> mockBoardList = List.of(TEST_BOARD1, TEST_BOARD2);
        Page<Board> mockPage = new PageImpl<>(mockBoardList, pageable, mockBoardList.size());

        when(boardRepository.findAllByReportCountLessThan(5, pageable)).thenReturn(mockPage);

        // when
        Page<BoardToListGetRes> res = boardService.getBoardAllList(page, size, sortBy, isAsc);

        // then
        assertEquals(1, res.getTotalPages());
        assertEquals(2, res.getTotalElements());
    }

    @Test
    @DisplayName("getBoardUserList 테스트")
    void getBoardUserListTest() {
        // given
        int page = 0;
        int size = 8;
        String sortBy = "createdAt";
        boolean isAsc = false;
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Board> mockBoardList = List.of(TEST_BOARD1, TEST_BOARD2);
        Page<Board> mockPage = new PageImpl<>(mockBoardList, pageable, mockBoardList.size());

        when(boardRepository.findAllByUserId(TEST_USER_ID, pageable)).thenReturn(mockPage);

        // when
        Page<BoardToReportGetRes> res =
                boardService.getBoardUserList(TEST_USER_ID, page, size, sortBy, isAsc);

        // then
        assertEquals(1, res.getTotalPages());
        assertEquals(2, res.getTotalElements());
    }

    @Test
    @DisplayName("getBoardBestList 테스트")
    void getBoardBestListTest() {
        // given
        List<Board> mockBoardList = List.of(TEST_BOARD1, TEST_BOARD2);

        when(boardRepository.findTop3ByReportCountLessThanOrderByLikeCountDesc(5))
                .thenReturn(mockBoardList);

        // when
        BoardBestListGetRes res = boardService.getBoardBestList();

        // then
        assertEquals(2, res.getBoards().size());
    }

    @Test
    @DisplayName("지도 저장 테스트")
    void saveMapImageTest() {
        // given
        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_MAP_IMAGE);

        // when
        String res = boardService.saveMapImage(multipartFile);

        // then
        assertEquals(TEST_MAP_IMAGE, res);
    }

    @Test
    @DisplayName("지도 삭제 테스트")
    void deleteMapImageTest() {
        // given

        // when
        boardService.deleteMapImage(TEST_MAP_IMAGE);

        // then
        verify(s3Util).deleteFile(TEST_MAP_IMAGE, FilePath.BOARD);
    }

    @Test
    @DisplayName("saveBoard 테스트")
    void saveBoardTest() {
        // given
        BoardSaveReq req =
                BoardSaveReq.builder()
                        .title(TEST_BOARD_TITLE)
                        .content(TEST_BOARD_CONTENT)
                        .departure(TEST_DEPARTURE)
                        .destination(TEST_DESTINATION)
                        .stopover(TEST_STOPOVER)
                        .region(TEST_REGION1)
                        .image(TEST_MAP_IMAGE)
                        .build();
        req.setUserId(TEST_USER_ID);

        when(userRepository.findById(TEST_USER_ID)).thenReturn(TEST_USER);
        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_IMAGE_URL);

        // when
        boardService.saveBoard(req, List.of(multipartFile));

        // then
        verify(boardRepository).save(argumentCaptor.capture());
        assertEquals(TEST_MAP_IMAGE, argumentCaptor.getValue().getFileURL());
        assertEquals(TEST_BOARD_TITLE, argumentCaptor.getValue().getTitle());
        assertEquals(TEST_BOARD_CONTENT, argumentCaptor.getValue().getContent());
        assertEquals(TEST_DEPARTURE, argumentCaptor.getValue().getDeparture());
        assertEquals(
                TEST_IMAGE.getImageUrl(), argumentCaptor.getValue().getImages().get(0).getImageUrl());
    }

    @Test
    @DisplayName("updateBoard 테스트")
    void updateBoardTest() {
        // given
        Board testBoard =
                Board.builder()
                        .user(TEST_USER)
                        .title(TEST_BOARD_TITLE)
                        .content(TEST_BOARD_CONTENT)
                        .departure(TEST_DEPARTURE)
                        .destination(TEST_DESTINATION)
                        .stopover(TEST_STOPOVER)
                        .region(TEST_REGION1)
                        .fileURL(TEST_MAP_IMAGE)
                        .build();

        BoardUpdateReq req = BoardUpdateReq.builder().title("수정 제목").content("수정 내용").image("").build();

        req.setUserId(TEST_USER_ID);
        req.setBoardId(TEST_BOARD_ID);

        when(boardRepository.findById(any())).thenReturn(testBoard);
        ReflectionTestUtils.setField(TEST_USER, "id", TEST_USER_ID);
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_IMAGE_URL);

        // when
        boardService.updateBoard(req, List.of(multipartFile));

        // then
        assertEquals("수정 제목", testBoard.getTitle());
    }

    @Test
    @DisplayName("deleteBoard 테스트")
    void deleteBoardTest() {
        // given
        when(boardRepository.findById(any())).thenReturn(TEST_BOARD);
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        ReflectionTestUtils.setField(TEST_BOARD, "id", TEST_BOARD_ID);
        ReflectionTestUtils.setField(TEST_USER, "id", TEST_USER_ID);

        // when
        boardService.deleteBoard(TEST_BOARD_ID, TEST_USER_ID);

        // then
        verify(boardRepository).delete(TEST_BOARD);
    }
}
