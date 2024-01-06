package sparta.com.sappun.domain.sample.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparta.com.sappun.domain.sample.dto.request.SampleSaveReq;
import sparta.com.sappun.domain.sample.dto.response.SampleGetRes;
import sparta.com.sappun.domain.sample.entity.Sample;
import sparta.com.sappun.domain.sample.repository.SampleRepository;
import sparta.com.sappun.test.SampleTest;

@ExtendWith(MockitoExtension.class)
class SampleServiceImplTest implements SampleTest {
    @Mock SampleRepository sampleRepository;

    @InjectMocks SampleServiceImpl sampleService;

    @Captor ArgumentCaptor<Sample> argumentCaptor; // 반환값이 없는 메소드를 테스트할 때 메소드 내의 객체를 캡처해 테스트 가능

    @Test
    @DisplayName("getSample 테스트")
    void getSampleTest() {
        // given
        given(sampleRepository.findById(TEST_SAMPLE_ID)).willReturn(TEST_SAMPLE);

        // when
        SampleGetRes res = sampleService.getSample(TEST_SAMPLE_ID);

        // then
        assertEquals(TEST_SAMPLE.getId(), res.getId());
        assertEquals(TEST_SAMPLE.getField1(), res.getField1());
        assertEquals(TEST_SAMPLE.getField2(), res.getField2());
    }

    @Test
    @DisplayName("saveSample 테스트")
    void saveSampleTest() {
        // given
        SampleSaveReq req =
                SampleSaveReq.builder().field1(TEST_SAMPLE_FILED1).field2(TEST_SAMPLE_FILED2).build();

        // when
        sampleService.saveSample(req);

        // then
        verify(sampleRepository).save(argumentCaptor.capture());
        assertEquals(TEST_SAMPLE_FILED1, argumentCaptor.getValue().getField1());
        assertEquals(TEST_SAMPLE_FILED2, argumentCaptor.getValue().getField2());
    }
}
