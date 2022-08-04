package com.example.kurlymarket_clone.controller;

import com.example.kurlymarket_clone.dto.SignupRequestDto;
import com.example.kurlymarket_clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public ResponseEntity registerUser(@RequestBody SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
       return new ResponseEntity<>("회원 가입 완료", HttpStatus.OK); // Error는 보통 전부 Exceptionhandler로 처리해주는데 굳이 ResponseEntity를 리턴해서
                                                                        //상태코드를 보내는 이유?
    }

    @GetMapping("/user/check")
    public ResponseEntity checkToken(){
        return new ResponseEntity<>("토큰 살아있음", HttpStatus.OK);
    }



    // 로그아웃
//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        return userService.logout(request);
//    }




}