package com.example.backend.user;

import com.example.backend.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    /**
     * 권한 변경 메서드
     *
     * @param userId 유저 식별자
     * @return
     */
    @Operation(summary = "권한 변경", description = "권한을 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "권한 변경 성공"),
            @ApiResponse(responseCode = "403", description = "변경할 권한이 없는 경우"),
            @ApiResponse(responseCode = "404", description = "변경할 유저를 찾지 못하는 경우")
    })
    @PatchMapping("/users/{userId}/roles")
    public ResponseEntity<UserResponseDto> changeRole(@PathVariable Long userId) {

        return ResponseEntity.ok(userService.changeRole(userId));
    }
}
