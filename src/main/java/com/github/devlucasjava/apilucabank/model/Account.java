package com.github.devlucasjava.apilucabank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany
    private List<Transactions> transactionsList;

    private BigDecimal balance =  BigDecimal.ZERO;

    private BigDecimal maxAmountTransaction = BigDecimal.valueOf(1000);

    @Column(nullable = false)
    private boolean isBlocked = false;
    private LocalDateTime blockedDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Users user;
}
