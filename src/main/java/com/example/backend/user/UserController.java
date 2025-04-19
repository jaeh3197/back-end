package com.example.backend.user;

import com.example.backend.user.dto.LoginRequestDto;
import com.example.backend.user.dto.LoginResponseDto;
import com.example.backend.user.dto.SignupRequestDto;
import com.example.backend.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;

    /**
     * 유저 생성 메서드
     *
     * @param userSignupRequestDto 유저 생성 정보
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody SignupRequestDto userSignupRequestDto) {

        return new ResponseEntity<>(userService.signup(userSignupRequestDto), HttpStatus.CREATED);
    }

    /**
     * 로그인 메서드
     *
     * @param loginRequestDto 로그인 정보
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        return new ResponseEntity<>(userService.login(loginRequestDto), HttpStatus.OK);
    }
}
