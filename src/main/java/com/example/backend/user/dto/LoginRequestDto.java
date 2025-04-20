package com.example.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @Schema(description = "이름", example = "test")
    private String username;

    @Schema(description = "비밀번호", example = "12341234")
    private String password;

    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
