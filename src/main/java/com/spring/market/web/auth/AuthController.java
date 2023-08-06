package com.spring.market.web.auth;

import com.spring.market.domain.user.dto.SignInDto;
import com.spring.market.domain.user.dto.UserInfoDto;
import com.spring.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/signin")
    public String signin() {
        return "auth/signin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    @PostMapping("/join")
    public String join(HttpServletRequest request){
        BCryptPasswordEncoder pw = new BCryptPasswordEncoder();

        SignInDto signInDto = new SignInDto();
        signInDto.setUsername(request.getParameter("email"));
        signInDto.setPassword(pw.encode(request.getParameter("password")));
        signInDto.setNickname(request.getParameter("nickname"));
        signInDto.setPhone_number(request.getParameter("phone"));
        System.out.println(signInDto);

        userService.join(signInDto);

        return "auth/signin";
    }

    @PostMapping("/findEmail")
    @ResponseBody
    public Map<String,String> findEmail (@RequestParam String email){
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername(email);
        Map<String,String> result = new HashMap<>();
        result.put("code",userService.findEmail(signInDto));

        return result;
    }

    @GetMapping("findById")
    public UserInfoDto findById (@RequestBody String login_id){

        return userService.findById(login_id);
    }


}
