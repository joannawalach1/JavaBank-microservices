package com.banking.userservice;

import com.banking.userservice.dto.UserCreateDto;
import com.banking.userservice.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
        existingUser.setPhoneNumber(userCreateDto.getPhoneNumber());
        existingUser.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(existingUser);
        return UserMapper.mapToResponseDto(savedUser);
    }
}
