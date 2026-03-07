package com.github.devlucasjava.apilucabank.service.validator;

import com.github.devlucasjava.apilucabank.exception.ForbiddenException;
import com.github.devlucasjava.apilucabank.model.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class AccountValidator {

    public boolean accountNotBlocked(Account account) {
        if (account.isBlocked()) {
            throw new ForbiddenException("Account blocked");
        }
        return true;
    }

    public boolean sufficientFunds(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new ForbiddenException("Your account insufficient funds");
        }
        return true;
    }

    public boolean maximumAmountNotExceeded(Account account, BigDecimal amount) {
        if (amount
                .compareTo(account.getMaxAmountTransaction()) > 0) {
            throw new ForbiddenException(
                    "You have exceeded the maximum transaction amount for your account. amount:"
                            + account.getMaxAmountTransaction().toString());
        }
        return true;
    }

    public boolean accountEqual(UUID account1, UUID account2) {
        if (account1 == account2) {
            throw new ForbiddenException("You can't perform a transvestite procedure on yourself");
        }
        return true;
    }
}
