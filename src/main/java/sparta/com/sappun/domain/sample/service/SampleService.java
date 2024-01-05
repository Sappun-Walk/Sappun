package sparta.com.sappun.domain.sample.service;

import sparta.com.sappun.domain.sample.dto.request.SampleDeleteReq;
import sparta.com.sappun.domain.sample.dto.request.SampleSaveReq;
import sparta.com.sappun.domain.sample.dto.request.SampleUpdateReq;
import sparta.com.sappun.domain.sample.dto.response.SampleDeleteRes;
import sparta.com.sappun.domain.sample.dto.response.SampleGetRes;
import sparta.com.sappun.domain.sample.dto.response.SampleSaveRes;
import sparta.com.sappun.domain.sample.dto.response.SampleUpdateRes;

public interface SampleService {
    SampleGetRes getSample(Long cardId);

    SampleSaveRes saveSample(SampleSaveReq req);

    SampleUpdateRes updateSample(SampleUpdateReq sampleUpdateReq);

    SampleDeleteRes deleteSample(SampleDeleteReq sampleDeleteReq);
}
