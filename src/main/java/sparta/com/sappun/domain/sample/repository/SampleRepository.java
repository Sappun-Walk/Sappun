package sparta.com.sappun.domain.sample.repository;

import org.springframework.data.repository.RepositoryDefinition;
import sparta.com.sappun.domain.sample.entity.Sample;

@RepositoryDefinition(domainClass = Sample.class, idClass = Long.class) // JPA 레포지토리의 기능을 제한
public interface SampleRepository {
    Sample save(Sample sample);

    void delete(Sample sample);

    Sample findById(Long sampleId);
}
