package sparta.com.sappun.domain.sample.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SampleGetRes {
    private Long id;
    private String field1;
    private String field2;

    @Builder
    private SampleGetRes(Long id, String field1, String field2) {
        this.id = id;
        this.field1 = field1;
        this.field2 = field2;
    }
}
