package sparta.com.sappun.domain.user.service;

import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.domain.user.dto.response.UserSignupRes;

public interface UserService {
    UserSignupRes signup(UserSignupReq req);
}
