package com.banking.userservice;

import com.banking.userservice.dto.UserCreateDto;
import com.banking.userservice.exceptions.NoDataException;
import com.banking.userservice.exceptions.UserWithThatEmailExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceValidator {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(UserCreateDto userCreateDto) throws NoDataException, UserWithThatEmailExists {
        if (userCreateDto.getUsername() == null || userCreateDto.getEmail() == null) {
            throw new NoDataException("Username and email cannot be null");
        }

        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new UserWithThatEmailExists("Email already exists: " + userCreateDto.getEmail());
        }
    }
}
