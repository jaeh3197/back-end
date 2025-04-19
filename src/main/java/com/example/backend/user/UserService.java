package com.example.backend.user;

import com.example.backend.global.JwtUtil;
import com.example.backend.global.error.errorcode.ErrorCode;
import com.example.backend.global.error.exception.CustomException;
import com.example.backend.user.dto.LoginRequestDto;
import com.example.backend.user.dto.LoginResponseDto;
import com.example.backend.user.dto.SignupRequestDto;
import com.example.backend.user.dto.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final List<User> users = new ArrayList<User>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final JwtUtil jwtUtil;

    /**
     * 유저 생성 로직
     *
     * @param userSignupRequestDto 유저 생성 정보
     * @return
     */
    public SignupResponseDto signup(SignupRequestDto userSignupRequestDto) {

        //유저 객체 생성
        User user = new User(userSignupRequestDto);

        //이미 가입한 유저인지 검증
        boolean existName = users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));

        //이미 가입한 유저 예외 처리
        if (existName) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }

        //유저 식별자 생성
        user.updateId(idCounter.getAndIncrement());

        //유저 역할 설정
        user.updateRole(Role.USER);

        //메모리에 저장
        users.add(user);

        return new SignupResponseDto(user);
    }

    public LoginResponseDto login(LoginRequestDto userLoginRequestDto) {

        String username = userLoginRequestDto.getUsername();
        User findUser = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        if (!findUser.getPassword().equals(userLoginRequestDto.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        return new LoginResponseDto(jwtUtil.generateToken(findUser.getUsername()));
    }
}
