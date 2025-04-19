package com.example.backend.user;

import com.example.backend.user.dto.SignupRequestDto;
import lombok.Getter;

@Getter
public class User {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private Role roles;

    public User() {
    }

    public User(Long id, String username, String password, String nickname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public User(SignupRequestDto userSignupRequestDto) {
        this.username = userSignupRequestDto.getUsername();
        this.password = userSignupRequestDto.getPassword();
        this.nickname = userSignupRequestDto.getNickname();
    }

    public void updateId(Long id) {
        this.id = id;
    }

    public void updateRole(Role roles) {
        this.roles = roles;
    }
}
