package sparta.com.sappun.domain.sample.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SampleSaveRes {
    private Long id;

    @Builder
    private SampleSaveRes(Long id) {
        this.id = id;
    }
}
