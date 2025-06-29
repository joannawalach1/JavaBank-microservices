package com.banking.userservice;

import com.banking.accountservice.dto.AccountResponseDto;
import com.banking.userservice.dto.UserCreateDto;
import com.banking.userservice.dto.UserLoginRequest;
import com.banking.userservice.dto.UserResponseDto;
import com.banking.userservice.dto.UserWithAccountsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RestTemplate restTemplate;

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

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        UserResponseDto user = userService.createUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/updatedUser")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserCreateDto userCreateDto) {
        UserResponseDto userResponseDto = userService.updateUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping("/{userId}/with-accounts")
    public ResponseEntity<UserWithAccountsDto> getUserWithAccounts(@PathVariable Long userId) {
        UserWithAccountsDto userWithAccounts = userService.getUserWithAccounts(userId);
        return ResponseEntity.ok(userWithAccounts);
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDto>> getUserAccounts(@PathVariable Long userId) {
        List<AccountResponseDto> accounts = userService.getUserAccounts(userId);
        return ResponseEntity.ok(accounts);
    }
}