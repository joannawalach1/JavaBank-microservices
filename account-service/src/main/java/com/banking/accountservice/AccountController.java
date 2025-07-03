package com.banking.accountservice;

import com.banking.accountservice.dto.AccountCreateDto;
import com.banking.accountservice.dto.AccountResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AccountResponseDto>> getUserAccountById(@PathVariable String userId) {
        List<AccountResponseDto> userAccount = accountService.getUserAccount(userId);
        return ResponseEntity.ok().body(userAccount);
    }

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
        Account accountByNumber = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok().body(accountByNumber);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountCreateDto accountCreateDto) {
        AccountResponseDto newAccount = accountService.createAccount(accountCreateDto);
        return ResponseEntity.ok().body(newAccount);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
        List<AccountResponseDto> allAccounts = accountService.getAllAccounts();
        return ResponseEntity.ok().body(allAccounts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Account> getAccountsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }


    @GetMapping("/id/{accountId}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long accountId) {
        AccountResponseDto account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }

}