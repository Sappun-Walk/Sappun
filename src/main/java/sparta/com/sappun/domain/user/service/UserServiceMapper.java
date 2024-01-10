package sparta.com.sappun.domain.user.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.user.dto.response.UserLoginRes;
import sparta.com.sappun.domain.user.dto.response.UserProfileRes;
import sparta.com.sappun.domain.user.dto.response.UserProfileUpdateRes;
import sparta.com.sappun.domain.user.entity.User;

@Mapper
public interface UserServiceMapper {
    UserServiceMapper INSTANCE = Mappers.getMapper(UserServiceMapper.class);

    UserLoginRes toUserLoginRes(User user);

    UserProfileRes toUserProfileRes(User user);

    UserProfileUpdateRes toUserProfileUpdateRes(User user);
}
