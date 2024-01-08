package sparta.com.sappun.domain.sample.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.sample.entity.Sample;
import sparta.com.sappun.test.SampleTest;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SampleRepositoryTest implements SampleTest {
    @Autowired private SampleRepository sampleRepository;

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given

        // when
        Sample saveSample = sampleRepository.save(TEST_SAMPLE);

        // then
        assertEquals(TEST_SAMPLE_FILED1, saveSample.getField1());
        assertEquals(TEST_SAMPLE_FILED2, saveSample.getField2());
    }

    @Test
    @DisplayName("id로 Sample 조회")
    void findByIdTest() {
        // given
        Sample saveSample = sampleRepository.save(TEST_SAMPLE);

        // when
        Sample sample = sampleRepository.findById(saveSample.getId());

        // then
        assertEquals(saveSample.getId(), sample.getId());
        assertEquals(saveSample.getField1(), sample.getField1());
        assertEquals(saveSample.getField2(), sample.getField2());
    }

    @Test
    @DisplayName("Sample 삭제 테스트")
    void deleteTest() {
        // given
        Sample saveSample = sampleRepository.save(TEST_SAMPLE);

        // when
        sampleRepository.delete(saveSample);
        Sample sample = sampleRepository.findById(TEST_SAMPLE_ID);

        // then
        assertThat(sample).isNull();
    }
}
