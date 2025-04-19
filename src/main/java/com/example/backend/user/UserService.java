package com.example.backend.user;

import com.example.backend.global.error.errorcode.ErrorCode;
import com.example.backend.global.error.exception.CustomException;
import com.example.backend.user.dto.UserSignupRequestDto;
import com.example.backend.user.dto.UserSignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class UserService {

    private final List<User> users = new ArrayList<User>();
    private final AtomicLong idCounter = new AtomicLong(1);

    /**
     * 유저 생성 로직
     *
     * @param userSignupRequestDto 유저 생성 정보
     * @return
     */
    public UserSignupResponseDto signup(UserSignupRequestDto userSignupRequestDto) {

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

        return new UserSignupResponseDto(user);
    }
}
