package com.example.backend.user.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private String username;

    private String password;

    private String nickname;

    public SignupRequestDto(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
