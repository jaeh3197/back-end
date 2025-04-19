package com.example.backend.user;

import com.example.backend.user.dto.UserResponseDto;
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

    @PatchMapping("/users/{userId}/roles")
    public ResponseEntity<UserResponseDto> changeRole(@PathVariable Long userId) {

        return ResponseEntity.ok(userService.changeRole(userId));
    }
}
