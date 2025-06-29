package com.banking.transactionservice;

import com.banking.transactionservice.dto.TransactionCreateRequest;
import com.banking.transactionservice.dto.TransactionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping("/newTransaction")
    public Transaction createTransaction(@RequestBody TransactionCreateRequest transactionCreateRequest) {
        return transactionService.createTransaction(transactionCreateRequest);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponseDto getTransactionById(@PathVariable String transactionId) {
        return transactionService.getTransactionsById(transactionId);
    }

    @GetMapping("/user/{userId}")
    public List<TransactionResponseDto> getTransactionByUserId(@PathVariable String userId) {
        return transactionService.getTransactionsByUserId(userId);
    }

    @GetMapping("/type/{transactionType}")
    public List<TransactionResponseDto> getTransactionByTransactionType(
            @RequestParam String id,
            @PathVariable String transactionType) {
        return transactionService.getTransactionsByType(id, transactionType);
    }



}
