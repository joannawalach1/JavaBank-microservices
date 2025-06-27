package com.banking.accountservice;

import com.banking.accountservice.dto.AccountCreateDto;
import com.banking.accountservice.dto.AccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<AccountResponseDto>> getUserAccountById(@PathVariable String userId) {
        List<AccountResponseDto> userAccount = accountService.getUserAccount(userId);
        return ResponseEntity.ok().body(userAccount);
    }

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<List<AccountResponseDto>> getAccountByNumber(@PathVariable String accountNumber) {
        List<AccountResponseDto> accountByNumber = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok().body(accountByNumber);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(AccountCreateDto accountCreateDto) {
        AccountResponseDto newAccount = accountService.createAccount(accountCreateDto);
        return ResponseEntity.ok().body(newAccount);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
        List<AccountResponseDto> allAccounts = accountService.getAllAccounts();
        return ResponseEntity.ok().body(allAccounts);
    }
}