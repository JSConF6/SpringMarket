package com.spring.market.config;

import com.spring.market.domain.user.UserMapper;
import com.spring.market.domain.user.dto.LoginDto;
import com.spring.market.domain.user.dto.UserInfoDto;
import com.spring.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(PrincipalDetailsService.class);

    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoDto userInfo = userMapper.findById(username).orElseThrow(
                () -> new InternalAuthenticationServiceException("존재하지 않는 계정입니다.")
        );

        return new PrincipalDetails(new LoginDto(userInfo));
    }
}
