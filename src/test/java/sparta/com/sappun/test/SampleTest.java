package sparta.com.sappun.test;

import sparta.com.sappun.domain.sample.entity.Sample;

public interface SampleTest {
    Long TEST_SAMPLE_ID = 1L;
    String TEST_SAMPLE_FILED1 = "field1";
    String TEST_SAMPLE_FILED2 = "field2";

    Sample TEST_SAMPLE =
            Sample.builder().field1(TEST_SAMPLE_FILED1).field2(TEST_SAMPLE_FILED2).build();
}
