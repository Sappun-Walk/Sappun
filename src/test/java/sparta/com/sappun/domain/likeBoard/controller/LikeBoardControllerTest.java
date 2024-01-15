package sparta.com.sappun.domain.likeBoard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.likeBoard.dto.response.LikeBoardSaveRes;
import sparta.com.sappun.domain.likeBoard.service.LikeBoardService;
import sparta.com.sappun.test.LikeBoardTest;

@WebMvcTest(controllers = {LikeBoardController.class})
class LikeBoardControllerTest extends BaseMvcTest implements LikeBoardTest {

    @MockBean private LikeBoardService likeBoardService;

    @Test
    @DisplayName("게시글 좋아요 저장 API 테스트")
    void likeBoard() throws Exception {
        // given
        LikeBoardSaveRes res = new LikeBoardSaveRes();
        when(likeBoardService.clickLikeBoard(any(), any())).thenReturn(res);

        // when-then
        mockMvc
                .perform(
                        post("/api/boards/{boardId}/like", TEST_BOARD_ID)
                                .principal(mockPrincipal)) // 실제 사용자 정보 제공 필요
                .andDo(print())
                .andExpect(status().isOk());
    }
}
