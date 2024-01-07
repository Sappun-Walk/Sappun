package sparta.com.sappun.domain.user.service;

import sparta.com.sappun.domain.sample.dto.request.SampleDeleteReq;
import sparta.com.sappun.domain.sample.dto.request.SampleSaveReq;
import sparta.com.sappun.domain.sample.dto.request.SampleUpdateReq;
import sparta.com.sappun.domain.sample.dto.response.SampleDeleteRes;
import sparta.com.sappun.domain.sample.dto.response.SampleGetRes;
import sparta.com.sappun.domain.sample.dto.response.SampleSaveRes;
import sparta.com.sappun.domain.sample.dto.response.SampleUpdateRes;
import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.domain.user.dto.response.UserSignupRes;

public interface UserService {
    UserSignupRes signup(UserSignupReq req);
}
