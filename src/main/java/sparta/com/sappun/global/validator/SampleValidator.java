package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.NOT_FOUND_SAMPLE;

import sparta.com.sappun.domain.sample.entity.Sample;
import sparta.com.sappun.global.exception.GlobalException;

public class SampleValidator {
    public static void validate(Sample sample) {
        if (isNullSample(sample)) {
            throw new GlobalException(NOT_FOUND_SAMPLE);
        }
    }

    private static boolean isNullSample(Sample sample) {
        return sample == null;
    }
}
