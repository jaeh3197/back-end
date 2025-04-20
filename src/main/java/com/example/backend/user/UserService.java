package com.example.backend.user;

import com.example.backend.global.JwtUtil;
import com.example.backend.global.error.errorcode.ErrorCode;
import com.example.backend.global.error.exception.CustomException;
import com.example.backend.user.dto.LoginRequestDto;
import com.example.backend.user.dto.LoginResponseDto;
import com.example.backend.user.dto.SignupRequestDto;
import com.example.backend.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public UserResponseDto signup(SignupRequestDto userSignupRequestDto) {

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
        if (userSignupRequestDto.getRole().equals("admin")) {
            user.updateRole(Role.ADMIN);
        } else {
            user.updateRole(Role.USER);
        }

        //메모리에 저장
        users.add(user);

        return new UserResponseDto(user);
    }

    /**
     * 로그인 로직
     *
     * @param userLoginRequestDto 로그인 정보
     * @return
     */
    public LoginResponseDto login(LoginRequestDto userLoginRequestDto) {

        // dto 에서 username 추출
        String username = userLoginRequestDto.getUsername();

        // 메모리에서 username 으로 조회
        User findUser = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        // 비밀번호 검증
        if (!findUser.getPassword().equals(userLoginRequestDto.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        return new LoginResponseDto(jwtUtil.generateToken(findUser.getUsername(), findUser.getRoles()));
    }

    /**
     * 유저 권한 변경 로직
     *
     * @param userId 변경할 유저 식별자
     * @return
     */
    public UserResponseDto changeRole(Long userId) {

        // 로그인 한 유저 권한
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //관리자 권한이 아닐 경우
        if (authentication.getAuthorities().stream()
                .noneMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        // 권한을 변경할 유저 식별자가 없는 경우
        User findUserById = users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        findUserById.updateRole(Role.ADMIN);

        return new UserResponseDto(findUserById);
    }
}
