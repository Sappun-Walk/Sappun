package sparta.com.sappun.domain.sample.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.sample.dto.response.SampleGetRes;
import sparta.com.sappun.domain.sample.dto.response.SampleSaveRes;
import sparta.com.sappun.domain.sample.entity.Sample;

@Mapper
public interface SampleServiceMapper {
    SampleServiceMapper INSTANCE = Mappers.getMapper(SampleServiceMapper.class);

    SampleGetRes toSampleGetRes(Sample sample);

    SampleSaveRes toSampleSavaRes(Sample sample);
}
