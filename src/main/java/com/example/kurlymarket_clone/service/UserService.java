package com.example.kurlymarket_clone.service;



import com.example.kurlymarket_clone.dto.SignupRequestDto;
import com.example.kurlymarket_clone.dto.SignupResponseDto;
import com.example.kurlymarket_clone.exception.CustomException;
import com.example.kurlymarket_clone.model.User;
import com.example.kurlymarket_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

import static com.example.kurlymarket_clone.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 회원가입
    public SignupResponseDto registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();
        String nickname = requestDto.getNickname();
        String pattern = "^[a-zA-Z0-9]*$";


//        회원 ID 중복 확인
        Optional<User> found = userRepository.findAllByUsername(username);
        if (found.isPresent()) {
            throw new CustomException(ID_DUPLICATION_CODE);
        }

        // 회원가입 조건
        if (username.length() < 3) {
            throw new CustomException(ID_LENGTH_CODE);
        } else if (!Pattern.matches(pattern, username)) {
            throw new CustomException(ID_FORM_CODE);
        } else if (!password.equals(passwordCheck)) {
            throw new CustomException(PASSWORD_CHECK_CODE);
        } else if (password.length() < 4) {
            throw new CustomException(PASSWORD_LENGTH_CODE);
        } else if (password.contains(username)) {
            throw new CustomException(PASSWORD_INCLUDE_CODE);
        }


        // 패스워드 인코딩
        password = passwordEncoder.encode(password);
        requestDto.setPassword(password);

        // 유저 정보 저장
        User user = new User(username, password);
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return new SignupResponseDto(username, password, passwordCheck, nickname);
    }



}