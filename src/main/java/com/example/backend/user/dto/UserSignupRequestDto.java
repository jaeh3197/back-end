package com.example.backend.user.dto;

import lombok.Getter;

@Getter
public class UserSignupRequestDto {

    private String username;

    private String password;

    private String nickname;

    public UserSignupRequestDto(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
