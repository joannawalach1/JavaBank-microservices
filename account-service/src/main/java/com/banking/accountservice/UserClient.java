package com.banking.accountservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${USER_SERVICE_URL}")
public interface UserClient {

    @GetMapping("/api/users/me")
    String getUserId(@RequestHeader("Authorization") String token);
}