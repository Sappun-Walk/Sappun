package sparta.com.sappun.test;

import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;

public interface UserTest {

    Long TEST_USER_ID = 1L;
    String TEST_USER_USERNAME = "username";
    String TEST_USER_NICKNAME = "nickname";
    String TEST_USER_PASSWORD = "abc123@";

    String TEST_USER_EMAIL = "aaa@aa.aa";

    User TEST_USER =
            User.builder()
                    .username(TEST_USER_USERNAME)
                    .nickname(TEST_USER_NICKNAME)
                    .password(TEST_USER_PASSWORD)
                    .email(TEST_USER_EMAIL)
                    .role(Role.USER)
                    .score(0)
                    .build();
}
