package com.spring.market.service;

import com.spring.market.config.PrincipalDetails;
import com.spring.market.domain.file.File;
import com.spring.market.domain.file.FileMapper;
import com.spring.market.domain.file.dto.SaveFileDto;
import com.spring.market.domain.file.dto.UpdateFileDto;
import com.spring.market.domain.user.User;
import com.spring.market.domain.user.UserMapper;
import com.spring.market.domain.user.dto.*;
import com.spring.market.handler.ex.CustomApiException;
import com.spring.market.handler.ex.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final FileMapper fileMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${file.path}")
    private String uploadPath;

    @Transactional
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

    public int findUserPw(FindPwDto findPwDto) {
        int count = userMapper.findUserPw(findPwDto);
        return count;
    }

    public void changePw(ChangePwDto changePwDto) {
        System.out.println("changePw START");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        changePwDto.setPassword(bCryptPasswordEncoder.encode(changePwDto.getPassword()));
        userMapper.changePw(changePwDto);
        System.out.println("changePw END");
    }

    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(LoginDto member) {
        UserInfoDto userInfo = userMapper.findUserProfileById(member.getLogin_id()).orElse(null);

        return userInfo;
    }

    @Transactional
    public int updateProfile(ProfileEditDto profile) {
        MultipartFile newFile = profile.getFile();
        if (!newFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();

            String originalFilename = newFile.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") - 1);

            String fileName = uuid + extension;

            String userHomeDir = System.getProperty("user.home");

            String folderPath = userHomeDir + java.io.File.separator + "upload" + java.io.File.separator;

            java.io.File f = new java.io.File(folderPath);

            if (!f.exists()) {
                if (!f.mkdir())
                    throw new CustomApiException("폴더 생성중 오류가 발생 했습니다.");
            }

            Path filePath = Paths.get(userHomeDir, "upload", fileName);

            try{
                Files.write(filePath, profile.getFile().getBytes());
            } catch (Exception e) {
                throw new CustomApiException("이미지 업로드 중 오류가 발생했습니다.");
            }

            profile.setPath(filePath.toString());
            profile.setOriginalName(originalFilename);
            profile.setName(fileName);

            File currentFile = fileMapper.findByUserId(profile.getId());

            if (currentFile == null) {
                SaveFileDto saveFileDto = new SaveFileDto();
                saveFileDto.setUserId(profile.getId());
                saveFileDto.setOriginalName(profile.getOriginalName());
                saveFileDto.setName(profile.getName());
                saveFileDto.setPath(profile.getPath());
                saveFileDto.setType("U");
                fileMapper.save(saveFileDto);
            } else {
                UpdateFileDto updateFileDto = new UpdateFileDto();
                updateFileDto.setId(currentFile.getId());
                updateFileDto.setOriginalName(profile.getOriginalName());
                updateFileDto.setName(profile.getName());
                updateFileDto.setPath(profile.getPath());
                fileMapper.updateFile(updateFileDto);
            }
        }

        UserInfoDto userInfo = userMapper.findUserProfileById(profile.getUsername()).orElseThrow(
                () -> new CustomException("유저가 존재하지 않습니다.")
        );

        Authentication currAuth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) currAuth.getPrincipal();

        LoginDto member = new LoginDto(userInfo);
        principalDetails.setMemberLoginDto(member);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(principalDetails, currAuth.getCredentials(), principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return userMapper.updateProfileById(profile);
    }
}
