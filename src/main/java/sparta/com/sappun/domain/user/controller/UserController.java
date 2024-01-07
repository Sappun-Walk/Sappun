package sparta.com.sappun.domain.user.controller;

import jakarta.validation.Valid;
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
import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.domain.user.dto.response.UserSignupRes;
import sparta.com.sappun.domain.user.service.UserService;
import sparta.com.sappun.global.CommonResponse;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public CommonResponse<UserSignupRes> signup(@RequestBody @Valid UserSignupReq req) { // TODO: 프로필 사진 입력받기
        return CommonResponse.success(userService.signup(req));
    }

}
