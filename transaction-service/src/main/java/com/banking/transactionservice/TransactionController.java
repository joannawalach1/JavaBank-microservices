package com.banking.transactionservice;

import com.banking.transactionservice.dto.TransactionCreateRequest;
import com.banking.transactionservice.dto.TransactionResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:8084")
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
    public TransactionResponseDto getTransactionById(@PathVariable UUID transactionId) {
        return transactionService.getTransactionsById(transactionId);
    }

    @GetMapping("/type/{transactionType}")
    public List<TransactionResponseDto> getTransactionByTransactionType(
            @RequestParam String id) {
        return transactionService.getTransactionsByType(id);
    }

    @GetMapping("/transaction")
    public List<TransactionResponseDto> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
}
