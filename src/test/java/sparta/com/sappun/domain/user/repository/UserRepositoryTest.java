package sparta.com.sappun.domain.user.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.test.UserTest;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest implements UserTest {

    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given

        // when
        User saveUser = userRepository.save(TEST_USER);

        // then
        assertEquals(TEST_USER_USERNAME, saveUser.getUsername());
        assertEquals(TEST_USER_NICKNAME, saveUser.getNickname());
    }

    @Test
    @DisplayName("findByUsername 테스트")
    void findByUsernameTest() {
        // given
        userRepository.save(TEST_USER);

        // when
        User saveUser = userRepository.findByUsername(TEST_USER_USERNAME);

        // then
        assertEquals(TEST_USER_USERNAME, saveUser.getUsername());
        assertEquals(TEST_USER_NICKNAME, saveUser.getNickname());
    }

    @Test
    @DisplayName("findById 테스트")
    void findByIdTest() {
        // given
        User user = userRepository.save(TEST_USER);

        // when
        User saveUser = userRepository.findById(user.getId());

        // then
        assertEquals(TEST_USER_USERNAME, saveUser.getUsername());
        assertEquals(TEST_USER_NICKNAME, saveUser.getNickname());
    }

    @Test
    @DisplayName("delete 테스트")
    void deleteTest() {
        // given
        User user = userRepository.save(TEST_USER);

        // when
        userRepository.delete(user);
        User findUser = userRepository.findById(user.getId());

        // then
        assertNull(findUser);
    }
}
