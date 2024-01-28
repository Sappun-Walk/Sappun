package sparta.com.sappun.test;

import java.util.List;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.RegionEnum;

public interface BoardTest extends UserTest {

    Long TEST_BOARD_ID = 1L;

    String TEST_BOARD_TITLE = "TEST_TITLE";
    String TEST_BOARD_CONTENT = "TEST_CONTENT";
    String TEST_BOARD_URL = "TEST_URL";
    String TEST_DEPARTURE = "TEST_DEPARTURE";
    String TEST_DESTINATION = "TEST_DESTINATION";
    List<String> TEST_STOPOVER = List.of("TEST1", "TEST2", "TEST3", "TEST4", "TEST5");
    RegionEnum TEST_REGION1 = RegionEnum.REGION1;
    String TEST_MAP_IMAGE = "https://board/mapImage.jpg";
    Integer TEST_LIKE_COUNT = 0;
    Integer TEST_REPORT_COUNT = 0;

    Board TEST_BOARD =
            Board.builder()
                    .user(TEST_USER)
                    .title(TEST_BOARD_TITLE)
                    .content(TEST_BOARD_CONTENT)
                    .fileURL(TEST_BOARD_URL)
                    .departure(TEST_DEPARTURE)
                    .destination(TEST_DESTINATION)
                    .stopover(TEST_STOPOVER)
                    .region(TEST_REGION1)
                    .likeCount(TEST_LIKE_COUNT)
                    .reportCount(TEST_REPORT_COUNT)
                    .build();

    Board TEST_BOARD1 =
            Board.builder()
                    .user(TEST_USER)
                    .title(TEST_BOARD_TITLE)
                    .content(TEST_BOARD_CONTENT)
                    .fileURL(TEST_BOARD_URL)
                    .departure(TEST_DEPARTURE)
                    .destination(TEST_DESTINATION)
                    .stopover(TEST_STOPOVER)
                    .region(TEST_REGION1)
                    .likeCount(1)
                    .reportCount(TEST_REPORT_COUNT)
                    .build();

    Board TEST_BOARD2 =
            Board.builder()
                    .user(TEST_USER)
                    .title(TEST_BOARD_TITLE)
                    .content(TEST_BOARD_CONTENT)
                    .fileURL(TEST_BOARD_URL)
                    .departure(TEST_DEPARTURE)
                    .destination(TEST_DESTINATION)
                    .stopover(TEST_STOPOVER)
                    .region(TEST_REGION1)
                    .likeCount(2)
                    .reportCount(TEST_REPORT_COUNT)
                    .build();
}
