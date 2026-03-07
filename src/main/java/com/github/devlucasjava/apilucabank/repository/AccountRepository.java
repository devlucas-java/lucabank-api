package com.github.devlucasjava.apilucabank.repository;

import com.github.devlucasjava.apilucabank.model.Account;
import com.github.devlucasjava.apilucabank.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByUser(Users user);
}
