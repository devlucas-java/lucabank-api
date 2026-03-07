package com.github.devlucasjava.apilucabank.dto.mapper;

import com.github.devlucasjava.apilucabank.dto.response.AccountSummaryResponse;
import com.github.devlucasjava.apilucabank.dto.response.TransactionResponse;
import com.github.devlucasjava.apilucabank.model.Account;
import com.github.devlucasjava.apilucabank.model.Transactions;
import com.github.devlucasjava.apilucabank.model.Users;

public class TransactionMapper {

    public static TransactionResponse toResponse(Transactions transactions, AccountSummaryResponse sender, AccountSummaryResponse receiver) {
        TransactionResponse transactionResponse = TransactionResponse.builder()
                .id(transactions.getId())
                .typeTransaction(transactions.getTypeTransaction())
                .amount(transactions.getAmount())
                .status(transactions.getStatus())
                .sender(sender)
                .receiver(receiver)
                .build();
        return transactionResponse;
    }

    public static AccountSummaryResponse createSummary(Account account, Users users) {
        AccountSummaryResponse summaryResponse = new AccountSummaryResponse(
                account.getId(), users.getFirstName() + users.getLastName()
        );
        return summaryResponse;
    }
}
