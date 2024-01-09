package sparta.com.sappun.domain.board.entity;

import lombok.Getter;

@Getter
public enum RegionEnum {
    REGION1("REGION1"),
    REGION2("REGION2"),
    REGION3("REGION3");

    private String region;

    RegionEnum(String region) {
        this.region = region;
    }

}
