package sparta.com.sappun.test;

import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.entity.UserSocialEnum;

public interface UserTest {

    Long TEST_USER_ID = 1L;
    String TEST_USER_USERNAME = "username";
    String TEST_USER_NICKNAME = "nickname";
    String TEST_USER_PASSWORD = "abc123@";
    String TEST_USER_NEW_PASSWORD = "cba321@";
    String TEST_USER_EMAIL = "aaa@aa.aa";
    String TEST_USER_PROFILE_URL = "resources/profile/image1.png";

    User TEST_USER =
            User.builder()
                    .username(TEST_USER_USERNAME)
                    .nickname(TEST_USER_NICKNAME)
                    .password(TEST_USER_PASSWORD)
                    .profileUrl(TEST_USER_PROFILE_URL)
                    .email(TEST_USER_EMAIL)
                    .role(Role.USER)
                    .social(UserSocialEnum.LOCAL)
                    .score(0)
                    .build();
}
