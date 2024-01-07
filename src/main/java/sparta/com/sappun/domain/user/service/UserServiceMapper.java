package sparta.com.sappun.domain.user.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.sample.dto.response.SampleGetRes;
import sparta.com.sappun.domain.sample.dto.response.SampleSaveRes;
import sparta.com.sappun.domain.sample.entity.Sample;

@Mapper
public interface UserServiceMapper {
    UserServiceMapper INSTANCE = Mappers.getMapper(UserServiceMapper.class);

}
