package com.banking;

import com.banking.userservice.JwtResponse;
import com.banking.userservice.UserRepository;
import com.banking.userservice.UserServiceApplication;
import com.banking.userservice.dto.UserCreateDto;
import com.banking.userservice.dto.UserLoginRequest;
import com.banking.userservice.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    public void shouldRegisterAndLoginUser() {
        // register
        UserCreateDto userCreateDto = new UserCreateDto("JoeD1", "joe@op.pl", "Joe", "Does", "password", "234567890");
        ResponseEntity<UserResponseDto> registerResponse = restTemplate.postForEntity(
                "/api/users/register",
                userCreateDto,
                UserResponseDto.class
        );

        assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
        assertNotNull(registerResponse.getBody());
        assertEquals("JoeD1", registerResponse.getBody().getUsername());
        assertEquals("joe@op.pl", registerResponse.getBody().getEmail());

        // login
        UserLoginRequest userLoginRequest = new UserLoginRequest("JoeD1", "password");

        ResponseEntity<JwtResponse> loginResponse = restTemplate.postForEntity(
                "/api/users/login",
                userLoginRequest,
                JwtResponse.class
        );

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());
    }
}



