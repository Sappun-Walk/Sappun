package sparta.com.sappun.domain.board.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
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

@WebMvcTest(controllers = {BoardController.class})
class BoardControllerTest extends BaseMvcTest implements BoardTest {
    @MockBean private BoardService boardService;
    static MockMultipartFile multipartFile;

    @BeforeAll
    static void setUpImage() throws IOException {
        String imageUrl = "static/images/image1.jpg";
        Resource fileResource = new ClassPathResource(imageUrl);

        multipartFile =
                new MockMultipartFile(
                        "image", fileResource.getFilename(), IMAGE_JPEG_VALUE, fileResource.getInputStream());
    }

    @Test
    @DisplayName("saveBoard 테스트")
    void saveBoardTest() throws Exception {
        // given
        BoardSaveReq boardSaveReq =
                BoardSaveReq.builder()
                        .title(TEST_BOARD_TITLE)
                        .content(TEST_BOARD_CONTENT)
                        .departure(TEST_DEPARTURE)
                        .stopover(TEST_STOPOVER)
                        .destination(TEST_DESTINATION)
                        .region(TEST_REGION1)
                        .image(TEST_MAP_IMAGE)
                        .build();

        //            List<MultipartFile> photoImages = List.of(multipartFile);
        //            boardSaveReq.setPhotoImages(photoImages);

        BoardSaveRes res = new BoardSaveRes();

        when(boardService.saveBoard(any(), any())).thenReturn(res);

        // when - then
        mockMvc
                .perform(
                        MockMvcRequestBuilders.multipart(HttpMethod.POST, "/api/boards")
                                .content(objectMapper.writeValueAsString(boardSaveReq))
                                .contentType(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("지도 파일 저장 테스트")
    void saveMapImageTest() throws Exception {
        // given
        when(boardService.saveMapImage(any())).thenReturn(TEST_MAP_IMAGE);

        // when - then
        mockMvc
                .perform(
                        MockMvcRequestBuilders.multipart(HttpMethod.POST, "/api/boards/map")
                                .file(multipartFile)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("지도 파일 삭제 테스트")
    void deleteMapImage() throws Exception {
        // when - then
        mockMvc
                .perform(
                        MockMvcRequestBuilders.multipart(
                                        HttpMethod.DELETE, "/api/boards/map?mapImage=" + TEST_MAP_IMAGE)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updateBoardTest() throws Exception {
        // given
        BoardUpdateReq req = BoardUpdateReq.builder().title("제목 수정").content("내용 수정").build();
        BoardUpdateRes res = new BoardUpdateRes();

        when(boardService.updateBoard(any(), any())).thenReturn(res);

        // when - then
        mockMvc
                .perform(
                        MockMvcRequestBuilders.multipart(HttpMethod.PATCH, "/api/boards/" + TEST_BOARD_ID)
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("deleteBoard 테스트")
    void deleteBoardTest() throws Exception {
        // given
        BoardDeleteRes res = new BoardDeleteRes();
        when(boardService.deleteBoard(any(), any())).thenReturn(res);

        // when - then
        mockMvc
                .perform(delete("/api/boards/{boardId}", TEST_BOARD_ID).principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
