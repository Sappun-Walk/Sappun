package sparta.com.sappun.domain.board.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegionEnum {
    REGION1("REGION1"),
    REGION2("REGION2"),
    REGION3("REGION3");

    private final String region;
}