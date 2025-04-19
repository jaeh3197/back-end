package com.example.backend.user.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private String username;

    private String password;

    private String nickname;

    private String role;

    public SignupRequestDto(String username, String password, String nickname, String role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }
}
