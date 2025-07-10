package com.banking.userservice;

import com.banking.userservice.dto.AccountResponseDto;
import com.banking.userservice.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final AccountClient accountClient;
    private final FullProfileClient fullProfileClient;

    public UserController(UserService userService, AccountClient accountClient, FullProfileClient fullProfileClient) {
        this.userService = userService;
        this.accountClient = accountClient;
        this.fullProfileClient = fullProfileClient;
    }

    @GetMapping("/username//{username}")
    public ResponseEntity<UserResponseDto> findUserByUsername(@PathVariable String username) {
        UserResponseDto userByUsername = userService.findUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(userByUsername);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
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
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request);
        UserResponseDto userDto = userService.findUserByUsername(request.getUsername());

        UserLoginResponse response = new UserLoginResponse(token, userDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updatedUser")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserCreateDto userCreateDto) {
        UserResponseDto userResponseDto = userService.updateUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping("/{username}/accounts")
    public ResponseEntity<UserWithAccountsDto> getUserWithAccounts(@PathVariable String username) {
        UserResponseDto user = userService.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<AccountResponseDto> accounts = accountClient.getAccountsForUser(user.getId());
        UserWithAccountsDto response = new UserWithAccountsDto(user, accounts);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserFullProfileDto> getUserFullProfile(@PathVariable String username) {
        UserFullProfileDto fullProfile = fullProfileClient.getUserFullProfile(username);
        return ResponseEntity.ok(fullProfile);
    }
}