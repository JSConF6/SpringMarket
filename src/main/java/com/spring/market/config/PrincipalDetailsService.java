package com.spring.market.config;

import com.spring.market.domain.user.dto.LoginDto;
import com.spring.market.domain.user.dto.UserInfoDto;
import com.spring.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("principalDetailService");

        System.out.println(username);

        UserInfoDto userInfoDto = userService.findById(username);

        System.out.println(userInfoDto);

        LoginDto memberLoginDto = new LoginDto();
        memberLoginDto.setLogin_id(userInfoDto.getUsername());
        memberLoginDto.setMember_password(userInfoDto.getPassword());
        return new PrincipalDetails(memberLoginDto);
    }
}
