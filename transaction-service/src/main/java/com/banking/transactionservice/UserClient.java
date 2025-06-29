package com.banking.transactionservice;

import com.banking.userservice.dto.UserResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    private final String userServiceUrl = "http://user-service/users";

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserResponseDto getUserByUsername(String username) {
        return restTemplate.getForObject(userServiceUrl + "/" + username, UserResponseDto.class);
    }
}
