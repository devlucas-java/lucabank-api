package com.github.devlucasjava.apilucabank.repository;

import com.github.devlucasjava.apilucabank.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {
}
