package sparta.com.sappun.domain.sample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.com.sappun.domain.sample.dto.request.SampleDeleteReq;
import sparta.com.sappun.domain.sample.dto.request.SampleSaveReq;
import sparta.com.sappun.domain.sample.dto.request.SampleUpdateReq;
import sparta.com.sappun.domain.sample.dto.response.SampleDeleteRes;
import sparta.com.sappun.domain.sample.dto.response.SampleGetRes;
import sparta.com.sappun.domain.sample.dto.response.SampleSaveRes;
import sparta.com.sappun.domain.sample.dto.response.SampleUpdateRes;
import sparta.com.sappun.domain.sample.service.SampleService;
import sparta.com.sappun.global.response.CommonResponse;

@RestController
@RequestMapping("/api/samples")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    //    @GetMapping("/{sampleId}")
    //    public CommonResponse<SampleGetRes> getSample(
    //        @PathVariable Long sampleId, @AuthenticationPrincipal UserDetailsImpl userDetails) { //
    // UserDetailsImpl이 현재 없음. 원래는 추가 필요.
    //        return RestResponse.success(sampleService.getSample(sampleId,
    // userDetails.getUser().getId()));
    //    }

    @GetMapping("/{sampleId}")
    public CommonResponse<SampleGetRes> getSample(@PathVariable Long sampleId) {
        return CommonResponse.success(sampleService.getSample(sampleId));
    }

    //    @PostMapping
    //    public CommonResponse<SampleSaveRes> saveSample(
    //        @RequestBody SampleSaveReq sampleSaveReq,
    //        @AuthenticationPrincipal UserDetailsImpl userDetails) {
    //        sampleSaveReq.setUserId(userDetails.getUser().getId()); // user Entity를 그대로 보내주기 보다는
    // dto에 담아서 전달
    //        return RestResponse.success(sampleService.saveSample(sampleSaveReq));
    //    }

    @PostMapping
    public CommonResponse<SampleSaveRes> saveSample(@RequestBody SampleSaveReq sampleSaveReq) {
        return CommonResponse.success(sampleService.saveSample(sampleSaveReq));
    }

    @PatchMapping
    public CommonResponse<SampleUpdateRes> updateSample(
            @RequestBody SampleUpdateReq sampleUpdateReq) {
        return CommonResponse.success(sampleService.updateSample(sampleUpdateReq));
    }

    @DeleteMapping
    public CommonResponse<SampleDeleteRes> deleteSample(
            @RequestBody SampleDeleteReq sampleDeleteReq) {
        return CommonResponse.success(sampleService.deleteSample(sampleDeleteReq));
    }
}
