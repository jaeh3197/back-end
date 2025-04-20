package com.example.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Schema(description = "이름", example = "test")
    private String username;

    @Schema(description = "비밀번호", example = "12341234")
    private String password;

    @Schema(description = "별명", example = "mentos")
    private String nickname;

    @Schema(description = "권한", example = "user")
    private String role;

    public SignupRequestDto(String username, String password, String nickname, String role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }
}
