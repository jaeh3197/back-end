package com.example.backend;

import com.example.backend.global.JwtUtil;
import com.example.backend.global.error.errorcode.ErrorCode;
import com.example.backend.global.error.exception.CustomException;
import com.example.backend.user.Role;
import com.example.backend.user.User;
import com.example.backend.user.UserService;
import com.example.backend.user.dto.LoginRequestDto;
import com.example.backend.user.dto.SignupRequestDto;
import com.example.backend.user.dto.UserResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        String secretKey = "4260704f6b3cdc12de11d4d095f1209339530b72b471b865509387966d39d9afbf91249483a961a25e889ed67777057489ff451f6df1f7b4b1954b8fca94e8c7edfce447668a2469f18723f14de35d40ed1476335de96c2eae35130889d82f08732d28aec663dc0acd34751f842e533db843224f8646fbef6a2598232e38d3705ba96df2c02057e982d75bac7d78deb8041cd9a1c73eb4f6bf2a79c0dea654c6c4484ebf130fb72a3b1e1005f4c015ae275fc24f8b831390f04cf58e36ad61ae2f5b1146d109813afa0bee9e2db68b26b7e74437fe950d27c83e2d2a66d9f35aa1e6e1ac3e845c6e27d71b0235cc54bdca2f4c163c0ec8a109c3f14b6606b313";
        JwtUtil jwtUtil = new JwtUtil(secretKey); // JwtUtil 초기화 (Mock 또는 실제 객체)
        userService = new UserService(jwtUtil);
    }

    @AfterEach
    void tearDown() {
        // 매 테스트 끝나고 SecurityContext 초기화
        SecurityContextHolder.clearContext();
    }

    @Test
    void signup() {
        // given
        SignupRequestDto signupRequestDto = new SignupRequestDto("testUser", "password", "mentos", "user");

        // when
        UserResponseDto response = userService.signup(signupRequestDto);

        // then
        assertThat(response.getUsername()).isEqualTo("testUser");
        assertThat(response.getRoles()).isEqualTo(Role.USER);
    }

    @Test
    void signup_failed() {
        // given
        SignupRequestDto signupRequestDto = new SignupRequestDto("duplicateUser", "password", "mentos", "user");
        userService.signup(signupRequestDto);

        // when & then
        assertThrows(
                RuntimeException.class, // CustomException이지만 대략적으로 여기선 RuntimeException으로
                () -> userService.signup(signupRequestDto)
        );
    }

    @Test
    void login() {
        // given
        SignupRequestDto signupRequestDto = new SignupRequestDto("loginUser", "password123", "mentos", "user");
        userService.signup(signupRequestDto);
        LoginRequestDto loginRequestDto = new LoginRequestDto("loginUser", "password123");

        // when
        String token = userService.login(loginRequestDto).getToken();

        // then
        assertThat(token).isNotNull();
    }

    @Test
    void login_failed() {
        // given
        SignupRequestDto signupRequestDto = new SignupRequestDto("wrongPasswordUser", "password", "mentos", "user");
        userService.signup(signupRequestDto);
        LoginRequestDto loginRequestDto = new LoginRequestDto("wrongPasswordUser", "wrongPassword");

        // when & then
        assertThrows(
                RuntimeException.class,
                () -> userService.login(loginRequestDto)
        );
    }

    @Test
    @DisplayName("ADMIN 권한으로 성공적으로 role 변경")
    void changeRoleSuccess() {
        // given
        User user = new User(1L, "testUser", "password", "mentos", Role.USER);

        setAuthentication("adminuser", "ADMIN");

        // when
        UserResponseDto result = userService.changeRole(1L);

        // then
        assertThat(result.getRoles()).isEqualTo(Role.ADMIN);
    }

    @Test
    @DisplayName("USER 권한으로 role 변경 시도 → 실패")
    void changeRoleAccessDenied() {
        // given
        User user = new User(2L, "testuser", "password", "user", Role.USER);

        setAuthentication("normaluser", "USER");

        // when & then
        assertThatThrownBy(() -> userService.changeRole(2L))
                .isInstanceOf(CustomException.class)
                .satisfies(e -> {
                    CustomException ex = (CustomException) e;
                    assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.ACCESS_DENIED);
                });
    }

    @Test
    @DisplayName("존재하지 않는 userId로 role 변경 시도 → 실패")
    void changeRoleUserNotFound() {
        setAuthentication("adminuser", "ADMIN");

        // when & then
        assertThatThrownBy(() -> userService.changeRole(999L))
                .isInstanceOf(CustomException.class)
                .satisfies(e -> {
                    CustomException ex = (CustomException) e;
                    assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND);
                });
    }

    // 유틸 메소드: 가짜 인증(Authentication) 세팅
    private void setAuthentication(String username, String role) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
