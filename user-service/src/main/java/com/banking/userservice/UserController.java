package com.banking.userservice;

import com.banking.userservice.dto.AccountResponseDto;
import com.banking.userservice.dto.*;
import com.banking.userservice.exceptions.InvalidLoginData;
import com.banking.userservice.exceptions.NoDataException;
import com.banking.userservice.exceptions.UserNotFound;
import com.banking.userservice.exceptions.UserWithThatEmailExists;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserService userService;
    private final AccountClient accountClient;
    private final TransactionClient fullProfileClient;
    private final JwtService jwtService;

    public UserController(UserService userService, AccountClient accountClient, TransactionClient fullProfileClient, JwtService jwtService) {
        this.userService = userService;
        this.accountClient = accountClient;
        this.fullProfileClient = fullProfileClient;
        this.jwtService = jwtService;
    }

    @GetMapping("/username//{username}")
    public ResponseEntity<UserResponseDto> findUserByUsername(@PathVariable String username) throws UserNotFound {
        UserResponseDto userByUsername = userService.findUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(userByUsername);
    }

    @GetMapping("/me")
    public ResponseEntity<UserFullProfileDto> getCurrentUser(HttpServletRequest request) {
        try {
            String token = jwtService.extractToken(request);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String userId = jwtService.extractUsername(token);
            UserFullProfileDto user = userService.getUserFullProfile(userId);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) throws NoDataException, UserWithThatEmailExists {
        UserResponseDto user = userService.createUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody UserLoginRequest request) throws InvalidLoginData {
        JwtResponse token = userService.login(request);
        return ResponseEntity.ok(new JwtResponse(token.getToken()));
    }

    @PostMapping("/updatedUser")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserCreateDto userCreateDto) {
        UserResponseDto userResponseDto = userService.updateUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping("/{username}/accounts")
    public ResponseEntity<UserWithAccountsDto> getUserWithAccounts(@PathVariable String username) throws UserNotFound {
        UserResponseDto user = userService.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<AccountResponseDto> accounts = accountClient.getAccountsForUser(user.getId());
        UserWithAccountsDto response = new UserWithAccountsDto(user.getUsername(),user.getEmail(), accounts);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/profile")
    public ResponseEntity<UserFullProfileDto> getUserFullProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);
        UserFullProfileDto fullProfile = userService.getUserFullProfile(username);
        return ResponseEntity.ok(fullProfile);
    }
}