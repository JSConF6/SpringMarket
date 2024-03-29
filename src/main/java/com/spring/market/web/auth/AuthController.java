package com.spring.market.web.auth;

import com.spring.market.domain.user.dto.ChangePwDto;
import com.spring.market.domain.user.dto.FindPwDto;
import com.spring.market.domain.user.dto.SignInDto;
import com.spring.market.domain.user.dto.UserInfoDto;
import com.spring.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String signin(@RequestParam(name = "error", required = false) String error,
                         @RequestParam(name = "exception", required = false) String exception,
                         Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "auth/signin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    @GetMapping("/changePw")
    public String changePw() {
        return "/changePw";
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

    @GetMapping("/findUserEmail")
    @ResponseBody
    public Map<String,String> findUserEmail (@RequestParam(name = "phone_number") String phone_number){
        System.out.println(phone_number);

        Map<String,String> result = new HashMap<>();
        String username = userService.findUserEmail(phone_number);
        if(username == null){
            result.put("code","400");
            result.put("username",username);
        }else{
            result.put("code","200");
            result.put("username",username);
        }

        return result;
    }

    @GetMapping("/findUserPw")
    @ResponseBody
    public Map<String,String> findUserPw (@ModelAttribute FindPwDto findPwDto){
        System.out.println(findPwDto);

        Map<String,String> result = new HashMap<>();
        int count = userService.findUserPw(findPwDto);
        if(count == 0){
            result.put("code","400");
        }else{
            result.put("code","200");
        }

        return result;
    }

    @PostMapping ("/changePw")
    @ResponseBody
    public Map<String,String> changePassword (@RequestBody ChangePwDto changePwDto){
        System.out.println(changePwDto);
        Map<String,String> result = new HashMap<>();
        userService.changePw(changePwDto);
        System.out.println("changePassword END");
        result.put("code","200");
        return result;
    }


}
