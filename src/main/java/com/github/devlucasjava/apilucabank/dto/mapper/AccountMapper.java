package com.github.devlucasjava.apilucabank.dto.mapper;

import com.github.devlucasjava.apilucabank.dto.response.AccountResponse;
import com.github.devlucasjava.apilucabank.model.Account;

public class AccountMapper {

    public static AccountResponse toResponse(Account account) {
        AccountResponse response = AccountResponse.builder()
                .id(account.getId())
                .transactionsList(account.getTransactionsList())
                .balance(account.getBalance())
                .maxAmountTransaction(account.getMaxAmountTransaction())
                .isBlocke(account.isBlocked())
                .blockedDate(account.getBlockedDate())
                .userId(account.getUser().getId())
                .build();
        return response;
    }
}
