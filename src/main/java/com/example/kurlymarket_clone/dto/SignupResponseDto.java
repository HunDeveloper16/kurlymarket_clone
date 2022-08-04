package com.example.kurlymarket_clone.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupResponseDto {
    private String username;
    private String password;
    private String passwordCheck;
    private String nickname;

    public SignupResponseDto(String username,String password,String passwordCheck, String nickname){
        this.username=username;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.nickname = nickname;
    }

}
