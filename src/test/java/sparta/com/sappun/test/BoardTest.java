package sparta.com.sappun.test;

import java.util.List;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.RegionEnum;

public interface BoardTest extends UserTest {

    Long TEST_BOARD_ID = 1L;

    String TEST_BOARD_TITLE = "TEST_TITLE";
    String TEST_BOARD_CONTENT = "TEST_CONTENT";
    String TEST_BOARD_URL = "TEST_URL";
    String TEST_DEPATRURE = "TEST_DEPATURE";
    String TEST_DESTINATION = "TEST_DESTINAION";
    List<String> TEST_STOPVER = List.of("TEST1", "TEST2", "TEST3", "TEST4", "TEST5");
    RegionEnum TEST_REGION1 = RegionEnum.REGION1;

    Integer TEST_LIKECOUNT = 0;
    Integer TEST_REPORTCOUNT = 0;

    Board TEST_BOARD =
            Board.builder()
                    .id(TEST_BOARD_ID)
                    .user(TEST_USER)
                    .title(TEST_BOARD_TITLE)
                    .content(TEST_BOARD_CONTENT)
                    .fileURL(TEST_BOARD_URL)
                    .departure(TEST_DEPATRURE)
                    .destination(TEST_DESTINATION)
                    .stopover(TEST_STOPVER)
                    .region(TEST_REGION1)
                    .likeCount(TEST_LIKECOUNT)
                    .reportCount(TEST_REPORTCOUNT)
                    .build();
}
