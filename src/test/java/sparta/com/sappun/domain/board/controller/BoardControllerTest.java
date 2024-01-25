package sparta.com.sappun.domain.board.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.board.dto.request.BoardSaveReq;
import sparta.com.sappun.domain.board.dto.request.BoardUpdateReq;
import sparta.com.sappun.domain.board.dto.response.*;
import sparta.com.sappun.domain.board.service.BoardService;
import sparta.com.sappun.test.BoardTest;

@Disabled
@WebMvcTest(controllers = {BoardController.class})
class BoardControllerTest extends BaseMvcTest implements BoardTest {
    @MockBean private BoardService boardService;
    static MockMultipartFile multipartFile;

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
    void getSampleTest() throws Exception {
        // given
        BoardGetRes res =
                BoardGetRes.builder()
                        .id(TEST_BOARD_ID)
                        .title(TEST_BOARD_TITLE)
                        .content(TEST_BOARD_CONTENT)
                        .fileURL(TEST_BOARD_URL)
                        .departure(TEST_DEPARTURE)
                        .stopover(TEST_STOPOVER)
                        .destination(TEST_DESTINATION)
                        .region(TEST_REGION1)
                        .likeCount(TEST_LIKE_COUNT)
                        .build();

        // when
        when(boardService.getBoard(any())).thenReturn(res);

        // then
        mockMvc
                .perform(get("/api/boards/{boardId}", TEST_BOARD_ID))
                .andDo(print())
                .andExpect(status().isOk());
    }
//
//    @Test
//    @DisplayName("saveBoard 테스트")
//    void saveBoardTest() throws Exception {
//        // given
//        BoardSaveReq boardSaveReq =
//                BoardSaveReq.builder()
//                        .title(TEST_BOARD_TITLE)
//                        .content(TEST_BOARD_CONTENT)
//                        .departure(TEST_DEPARTURE)
//                        .stopover(TEST_STOPOVER)
//                        .destination(TEST_DESTINATION)
//                        .region(TEST_REGION1)
//                        .build();
//
//        MockMultipartFile req =
//                new MockMultipartFile(
//                        "data",
//                        null,
//                        "application/json",
//                        objectMapper.writeValueAsString(boardSaveReq).getBytes(StandardCharsets.UTF_8));
//
//        BoardSaveRes res = new BoardSaveRes();
//
//        // when
//        when(boardService.saveBoard(any(), any())).thenReturn(res);
//
//        // then
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders.multipart(HttpMethod.POST, "/api/boards")
//                                .file(multipartFile)
//                                .file(req)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.MULTIPART_FORM_DATA)
//                                .principal(mockPrincipal))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

    @Test
    @DisplayName("getBoards 테스트")
    void getBoardsTest() throws Exception {
        // given
        //        BoardToListGetRes res =
        //                BoardToListGetRes.builder()
        //                        .id(TEST_BOARD_ID)
        //                        .title(TEST_BOARD_TITLE)
        //                        .fileURL(TEST_BOARD_URL)
        //                        .departure(TEST_DEPARTURE)
        //                        .stopover(TEST_STOPOVER)
        //                        .destination(TEST_DESTINATION)
        //                        .region(TEST_REGION1)
        //                        .likeCount(TEST_LIKECOUNT)
        //                        .build();

        // when

        // then
    }

    @Test
    @DisplayName("getBestBoards 테스트")
    void getBestBoardsTest() throws Exception {
        // given

        // when

        // then
    }
//
//    @Test
//    @DisplayName("updateBoard 테스트")
//    void updateBoardTest() throws Exception {
//        // given
//        BoardUpdateReq boardUpdateReq =
//                BoardUpdateReq.builder()
//                        .boardId(TEST_BOARD_ID)
//                        .title(TEST_BOARD_TITLE)
//                        .content(TEST_BOARD_CONTENT)
//                        .departure(TEST_DEPARTURE)
//                        .stopover(TEST_STOPOVER)
//                        .destination(TEST_DESTINATION)
//                        .region(TEST_REGION1)
//                        .build();
//
//        MockMultipartFile req =
//                new MockMultipartFile(
//                        "data",
//                        null,
//                        "application/json",
//                        objectMapper.writeValueAsString(boardUpdateReq).getBytes(StandardCharsets.UTF_8));
//
//        BoardUpdateRes res = new BoardUpdateRes();
//        // when
//
//        when(boardService.updateBoard(any(), any())).thenReturn(res);
//
//        // then
//
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders.multipart(
//                                        HttpMethod.PATCH, "/api/boards/{boardId}", TEST_BOARD_ID)
//                                .file(multipartFile)
//                                .file(req)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.MULTIPART_FORM_DATA)
//                                .principal(mockPrincipal))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

    @Test
    @DisplayName("deleteBoard 테스트")
    void deleteBoardTest() throws Exception {
        // given
        BoardDeleteRes res = new BoardDeleteRes();
        when(boardService.deleteBoard(any(), any())).thenReturn(res);

        // when
        // then
        mockMvc
                .perform(delete("/api/boards/{boardId}", TEST_BOARD_ID).principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
