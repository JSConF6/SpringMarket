package com.spring.market.service;

import com.spring.market.config.PrincipalDetails;
import com.spring.market.domain.user.User;
import com.spring.market.domain.user.UserMapper;
import com.spring.market.domain.user.dto.*;
import com.spring.market.handler.ex.CustomApiException;
import com.spring.market.handler.ex.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public void join(SignInDto signInDto){
        userMapper.join(signInDto);
    }
    public String findEmail(SignInDto signInDto){
        int count = userMapper.findMember(signInDto);
        if(count > 0){
            return "001";
        }else {
            return "000";
        }
    }

    @Transactional
    public void passwordUpdate(LoginDto loginDto, PasswordChangeDto passwordChangeDto) {
        User updateUser = userMapper.findByUsername(loginDto.getLogin_id()).orElseThrow(
                () -> new CustomApiException("알 수 없는 오류로 인해 비밀번호 변경을 진행할 수 없습니다. 관리자에게 문의해주세요.")
        );

        if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), updateUser.getPassword())) {
            throw new CustomApiException("현재 비밀번호가 맞지 않습니다. 다시 확인해주세요");
        }

        if (passwordEncoder.matches(passwordChangeDto.getChangePassword(), updateUser.getPassword())) {
            throw new CustomApiException("현재 비밀번호와 변경 비밀번호가 같습니다.");
        }

        String encPassword = passwordEncoder.encode(passwordChangeDto.getChangePassword());
        updateUser.setPassword(encPassword);
        userMapper.updateById(updateUser);
    }

    @Transactional
    public void withdraw(LoginDto loginDto, String currentPassword) {

        User withdrawUser = userMapper.findByUsername(loginDto.getLogin_id()).orElseThrow(
                () -> new CustomApiException("알 수 없는 오류로 인해 탈퇴를 진행할 수 없습니다. 관리자에게 문의해주세요.")
        );

        if (!passwordEncoder.matches(currentPassword, withdrawUser.getPassword())) {
            throw new CustomApiException("현재 비밀번호가 맞지 않습니다. 다시 확인 해주세요");
        }

        userMapper.withdraw(loginDto.getId());
    }

    public UserInfoDto findById(String login_id) {
        UserInfoDto userInfo = userMapper.findById(login_id).orElseThrow(
                () -> new RuntimeException("아이디 찾기 실패")
        );
        return userInfo;
    }

    public String findUserEmail(String phone_number) {
        String username = userMapper.findUserEmail(phone_number);
        if(username == null || username.equals("")){
            return null;
        }else{
            return username;
        }


    }

    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(LoginDto member) {
        UserInfoDto userInfo = userMapper.findById(member.getLogin_id()).orElseThrow(
                () -> new CustomApiException("알 수 없는 오류가 발생 했습니다. 관리자에게 문의해주세요.")
        );

        return userInfo;
    }

    @Transactional
    public void updateProfile(ProfileEditDto profile) {
        userMapper.updateProfileById(profile);

        UserInfoDto userInfo = userMapper.findById(profile.getUsername()).orElseThrow(
                () -> new CustomException("유저가 존재하지 않습니다.")
        );

        Authentication currAuth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) currAuth.getPrincipal();

        LoginDto member = new LoginDto(userInfo);
        principalDetails.setMemberLoginDto(member);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(principalDetails, currAuth.getCredentials(), principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
