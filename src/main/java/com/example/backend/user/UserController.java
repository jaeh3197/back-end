package com.example.backend.user;

import com.example.backend.user.dto.UserSignupRequestDto;
import com.example.backend.user.dto.UserSignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UserSignupResponseDto> createUser(@RequestBody UserSignupRequestDto userSignupRequestDto) {

        return new ResponseEntity<>(userService.signup(userSignupRequestDto), HttpStatus.CREATED);
    }
}
