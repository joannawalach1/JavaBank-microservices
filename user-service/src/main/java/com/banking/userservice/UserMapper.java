package com.banking.userservice;

import com.banking.userservice.dto.UserCreateDto;
import com.banking.userservice.dto.UserResponseDto;

public class UserMapper {
    public static UserResponseDto mapToResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getSurname());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setPassword(user.getPassword());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    public static User mapToEntity(UserCreateDto userResponseDto) {
        User user = new User();
        user.setUsername(userResponseDto.getUsername());
        user.setEmail(userResponseDto.getEmail());
        user.setFirstName(userResponseDto.getFirstName());
        user.setSurname(userResponseDto.getSurname());
        user.setPhoneNumber(userResponseDto.getPhoneNumber());
        user.setPassword(userResponseDto.getPassword());
        return user;
    }
}
