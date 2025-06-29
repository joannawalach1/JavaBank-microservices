package com.banking.userservice;

import com.banking.accountservice.dto.AccountResponseDto;
import com.banking.userservice.dto.UserCreateDto;
import com.banking.userservice.dto.UserLoginRequest;
import com.banking.userservice.dto.UserResponseDto;
import com.banking.userservice.dto.UserWithAccountsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AccountServiceClient accountServiceClient;

    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::mapToResponseDto)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        if (userCreateDto.getUsername() == null || userCreateDto.getEmail() == null) {
            throw new IllegalArgumentException("Username and email cannot be null");
        }

        if (userRepository.existsByUsername(userCreateDto.getUsername())) {
            throw new RuntimeException("Username already exists: " + userCreateDto.getUsername());
        }

        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new RuntimeException("Email already exists: " + userCreateDto.getEmail());
        }

        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstName());
        user.setSurname(userCreateDto.getSurname());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setPhoneNumber(userCreateDto.getPhoneNumber());
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return UserMapper.mapToResponseDto(savedUser);
    }

    public UserResponseDto updateUser(UserCreateDto userCreateDto) {
        if (userCreateDto.getUsername() == null) {
            throw new IllegalArgumentException("Username must be provided for update");
        }

        User existingUser = userRepository.findByUsername(userCreateDto.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User not found with username: " + userCreateDto.getUsername()));

        existingUser.setEmail(userCreateDto.getEmail());
        existingUser.setFirstName(userCreateDto.getFirstName());
        existingUser.setSurname(userCreateDto.getSurname());
        existingUser.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        existingUser.setPhoneNumber(userCreateDto.getPhoneNumber());
        existingUser.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(existingUser);
        return UserMapper.mapToResponseDto(savedUser);
    }

    public String login(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        return jwtService.generateToken(user);
    }

    public UserWithAccountsDto getUserWithAccounts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<AccountResponseDto> accounts = accountServiceClient.getAccountsByUserId(userId);

        return new UserWithAccountsDto(
                user.getUsername(),
                user.getEmail(),
                accounts
        );
    }

    public List<AccountResponseDto> getUserAccounts(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        return accountServiceClient.getAccountsByUserId(userId);
    }
}
