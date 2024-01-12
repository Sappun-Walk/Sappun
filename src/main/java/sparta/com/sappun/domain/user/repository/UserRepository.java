package sparta.com.sappun.domain.user.repository;

import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.user.entity.User;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository {
    User save(User user);

    User findByUsername(String username);

    User findById(Long id);

    void delete(User user);

    Boolean existsByUsername(String username);

    Boolean existsByNickname(String nickname);

    Boolean existsByEmail(String email);
}
