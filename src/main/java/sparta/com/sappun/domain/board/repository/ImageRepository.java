package sparta.com.sappun.domain.board.repository;

import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.board.entity.Image;

@RepositoryDefinition(domainClass = Image.class, idClass = Long.class)
public interface ImageRepository {

    Image findById(Long imageId);

    Image save(Image image);

    // 기타 필요한 쿼리 메서드 추가
}