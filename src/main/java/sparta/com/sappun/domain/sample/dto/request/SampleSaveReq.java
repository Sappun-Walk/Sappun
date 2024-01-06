package sparta.com.sappun.domain.sample.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SampleSaveReq {
    private String field1;
    private String field2;
    private Long userId;

    @Builder
    private SampleSaveReq(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}
