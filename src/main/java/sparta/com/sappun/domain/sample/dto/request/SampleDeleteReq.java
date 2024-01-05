package sparta.com.sappun.domain.sample.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SampleDeleteReq {
    private Long id;
    private Long userId;

    @Builder
    private SampleDeleteReq(Long id) {
        this.id = id;
    }
}
