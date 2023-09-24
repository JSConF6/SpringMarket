package com.spring.market.domain.user;

import com.spring.market.domain.user.dto.ChangePwDto;
import com.spring.market.domain.user.dto.FindPwDto;
import com.spring.market.domain.user.dto.ProfileEditDto;
import com.spring.market.domain.user.dto.SignInDto;
import com.spring.market.domain.user.dto.UserInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    int findMember(SignInDto signInDto);

    void join(SignInDto signInDto);

    Optional<User> findByUsernameAndPassword(User user);

    Optional<User> findByUsername(String username);

    void updateById(User user);

    void withdraw(int id);

    Optional<UserInfoDto> findById(String login_id);
    Optional<UserInfoDto> findUserProfileById(String login_id);

    String findUserEmail(String phone_number);

    int findUserPw(FindPwDto findPwDto);

    void changePw(ChangePwDto changePwDto);

    int updateProfileById(ProfileEditDto profileEditDto);
}
