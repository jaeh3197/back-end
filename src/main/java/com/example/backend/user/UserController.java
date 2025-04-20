package com.example.backend.user;

import com.example.backend.user.dto.LoginRequestDto;
import com.example.backend.user.dto.LoginResponseDto;
import com.example.backend.user.dto.SignupRequestDto;
import com.example.backend.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저", description = "유저 API")
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
    @Operation(summary = "유저 생성", description = "유저를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "유저 생성 성공"),
            @ApiResponse(responseCode = "409", description = "이미 가입한 유저일 경우")
    })
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
    @Operation(summary = "로그인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "아이디 혹은 비밀번호가 잘못된 경우")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        return new ResponseEntity<>(userService.login(loginRequestDto), HttpStatus.OK);
    }
}
