package com.banking.userservice;

import com.banking.userservice.dto.TransactionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "transaction-service", url = "http://localhost:8082")
public interface TransactionClient {

    @GetMapping("/transactions/user/{userId}")
    public List<TransactionResponseDto> getUserFullProfile(String username);
}