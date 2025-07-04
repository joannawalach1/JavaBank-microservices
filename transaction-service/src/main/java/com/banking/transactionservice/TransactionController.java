package com.banking.transactionservice;

import com.banking.transactionservice.dto.TransactionCreateRequest;
import com.banking.transactionservice.dto.TransactionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping
    public Transaction createTransaction(@RequestBody TransactionCreateRequest transactionCreateRequest) {
        return transactionService.createTransaction(transactionCreateRequest);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponseDto getTransactionById(@PathVariable Long transactionId) {
        return transactionService.getTransactionsById(transactionId);
    }

    @GetMapping("/type/{transactionType}")
    public List<TransactionResponseDto> getTransactionByTransactionType(
            @RequestParam String id,
            @PathVariable String transactionType) {
        return transactionService.getTransactionsByType(id, transactionType);
    }
}
