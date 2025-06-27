package com.banking.userservice;

import com.banking.userservice.dto.UserCreateDto;
import com.banking.userservice.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> findUserByUsername(@PathVariable String username) {
        UserResponseDto userByUsername = userService.findUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(userByUsername);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAllUsers() {
        List<UserResponseDto> allUsers = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        UserResponseDto user = userService.createUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/updatedUser")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserCreateDto userCreateDto) {
        UserResponseDto userResponseDto = userService.updateUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);

    }
}