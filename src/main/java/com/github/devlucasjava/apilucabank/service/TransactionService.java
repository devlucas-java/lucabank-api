package com.github.devlucasjava.apilucabank.service;

import com.github.devlucasjava.apilucabank.dto.mapper.TransactionMapper;
import com.github.devlucasjava.apilucabank.dto.request.CreateTransactionRequest;
import com.github.devlucasjava.apilucabank.dto.response.AccountSummaryResponse;
import com.github.devlucasjava.apilucabank.dto.response.TransactionResponse;
import com.github.devlucasjava.apilucabank.exception.ResourceNotFoundException;
import com.github.devlucasjava.apilucabank.model.*;
import com.github.devlucasjava.apilucabank.repository.AccountRepository;
import com.github.devlucasjava.apilucabank.repository.TransactionsRepository;
import com.github.devlucasjava.apilucabank.service.validator.AccountValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionsRepository transactionsRepository;
    private final AccountValidator accountValidator;

    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest transactionRequest, Users users) {
        Account accountReceiver = accountRepository.findById(transactionRequest.getReceiverId()).
                orElseThrow(() -> new ResourceNotFoundException("Account receiver not found"));
        Account accountSender = accountRepository.findByUser(users)
                .orElseThrow(() -> new ResourceNotFoundException("Account sender Not Found"));

        Transactions transactions = Transactions.builder()
                .timeStampSender(LocalDateTime.now())
                .amount(transactionRequest.getAmount())
                .typeTransaction(TypeTransaction.TRANSFER)
                .receiver(accountReceiver)
                .sender(accountSender)
                .description(transactionRequest.getDescription())
                .build();

        accountValidator.accountNotBlocked(accountSender);
        accountValidator.accountNotBlocked(accountReceiver);
        accountValidator.sufficientFunds(accountSender, transactionRequest.getAmount());
        accountValidator.maximumAmountNotExceeded(accountSender, transactionRequest.getAmount());
        accountValidator.accountEqual(accountReceiver.getId(), accountSender.getId());

        accountSender.setBalance(
                accountSender.getBalance().subtract(transactionRequest.getAmount()));
        accountReceiver.setBalance(
                accountReceiver.getBalance().add(transactionRequest.getAmount()));

        transactions.setTimeStampReceiver(LocalDateTime.now());
        transactions.setStatus(StatusEnum.COMPLETE);

        transactionsRepository.save(transactions);
        accountRepository.save(accountSender);
        accountRepository.save(accountReceiver);

        AccountSummaryResponse sender = TransactionMapper.createSummary(accountSender, users);
        AccountSummaryResponse receiver = TransactionMapper.createSummary(accountReceiver, accountReceiver.getUser());

        return TransactionMapper.toResponse(transactions, sender, receiver);
    }
}
