package sparta.com.sappun.domain.sample.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.sample.dto.response.SampleGetRes;
import sparta.com.sappun.domain.sample.dto.response.SampleSaveRes;
import sparta.com.sappun.domain.sample.service.SampleService;
import sparta.com.sappun.test.SampleTest;

@WebMvcTest(controllers = {SampleController.class})
class SampleControllerTest extends BaseMvcTest implements SampleTest {
    @MockBean private SampleService sampleService;

    @Test
    @DisplayName("getSample 테스트")
    void getSampleTest() throws Exception {
        // given
        SampleGetRes res =
                SampleGetRes.builder()
                        .id(TEST_SAMPLE_ID)
                        .field1(TEST_SAMPLE_FILED1)
                        .field2(TEST_SAMPLE_FILED2)
                        .build();

        // when
        when(sampleService.getSample(any())).thenReturn(res);

        // then
        mockMvc
                .perform(get("/api/samples/{sampleId}", TEST_SAMPLE_ID))
                // .principal(mockPrincipal)) UserDetails 추가 이후
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("saveSample 테스트")
    void saveSampleTest() throws Exception {
        // given
        SampleSaveRes res = SampleSaveRes.builder().sampleId(TEST_SAMPLE_ID).build();

        // when
        when(sampleService.saveSample(any())).thenReturn(res);

        // then
        mockMvc
                .perform(post("/api/samples"))
                // .principal(mockPrincipal)) UserDetails 추가 이후
                .andDo(print())
                .andExpect(status().isOk());
    }
}
