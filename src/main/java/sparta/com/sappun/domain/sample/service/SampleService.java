package sparta.com.sappun.domain.sample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.sample.dto.request.SampleDeleteReq;
import sparta.com.sappun.domain.sample.dto.request.SampleSaveReq;
import sparta.com.sappun.domain.sample.dto.request.SampleUpdateReq;
import sparta.com.sappun.domain.sample.dto.response.SampleDeleteRes;
import sparta.com.sappun.domain.sample.dto.response.SampleGetRes;
import sparta.com.sappun.domain.sample.dto.response.SampleSaveRes;
import sparta.com.sappun.domain.sample.dto.response.SampleUpdateRes;
import sparta.com.sappun.domain.sample.entity.Sample;
import sparta.com.sappun.domain.sample.repository.SampleRepository;
import sparta.com.sappun.global.validator.SampleValidator;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    @Transactional(readOnly = true)
    public SampleGetRes getSample(Long sampleId) {
        Sample sample = getSampleById(sampleId);

        return SampleServiceMapper.INSTANCE.toSampleGetRes(
                sample); // 반환하고자 하는 dto로 entity를 변환하는데 Mapper를 사용
    }

    @Transactional
    public SampleSaveRes saveSample(SampleSaveReq sampleSaveReq) {
        return SampleServiceMapper.INSTANCE.toSampleSavaRes(
                sampleRepository.save(
                        Sample.builder()
                                .field1(sampleSaveReq.getField1())
                                .field2(sampleSaveReq.getField2())
                                .build()));
    }

    @Transactional
    public SampleUpdateRes updateSample(SampleUpdateReq sampleUpdateReq) {
        Sample sample = getSampleById(sampleUpdateReq.getId());

        // UserDetails로 받아온 사용자(로그인한 사용자)와 기존 sample의 작성자가 동일 인물이거나 관리자일 때만 수정 가능하도록 로직 추가 필요

        sample.update(sampleUpdateReq);
        return new SampleUpdateRes(); // 반환할 값이 없을 때 생성자를 이용해 생성한 빈 객체를 반환
    }

    @Transactional
    public SampleDeleteRes deleteSample(SampleDeleteReq sampleDeleteReq) {
        Sample sample = getSampleById(sampleDeleteReq.getId());

        // UserDetails로 받아온 사용자(로그인한 사용자)와 기존 sample의 작성자가 동일 인물이거나 관리자일 때만 삭제 가능하도록 로직 추가 필요

        sampleRepository.delete(sample);
        return new SampleDeleteRes();
    }

    private Sample getSampleById(Long sampleId) {
        Sample sample = sampleRepository.findById(sampleId); // DB에 sample이 존재하지 않으면 sample == null
        SampleValidator.validate(sample); // SampleValidator에서 sample이 null인지 확인하고 null이면 예외 처리
        return sample;
    }
}
