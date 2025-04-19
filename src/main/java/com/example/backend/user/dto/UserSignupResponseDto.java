package com.example.backend.user.dto;

import com.example.backend.user.Role;
import com.example.backend.user.User;
import lombok.Getter;

@Getter
public class UserSignupResponseDto {

    private String username;

    private String nickname;

    private Role roles;

    public UserSignupResponseDto(String username, String nickname, Role roles) {
        this.username = username;
        this.nickname = nickname;
        this.roles = roles;
    }

    public UserSignupResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.roles = user.getRoles();
    }
}
