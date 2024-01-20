package sparta.com.sappun.domain.board.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegionEnum {
    // 지역명 입력
    REGION1("서울특별시"),
    REGION2("부산광역시"),
    REGION3("대구광역시"),
    REGION4("인천광역시"),
    REGION5("광주광역시"),
    REGION6("울산광역시"),
    REGION7("세종특별차치시"),
    REGION8("경기도"),
    REGION9("강원특별자치도"),
    REGION10("충청북도"),
    REGION11("충청남도"),
    REGION12("전북특별차치도"),
    REGION13("전라남도"),
    REGION14("경상북도"),
    REGION15("경상남도"),
    REGION16("제주특별자치도");

    private final String region;
}
