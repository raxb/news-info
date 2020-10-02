package com.newsinfo.model;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Transactional properties provider
 */
@Getter
public class TransactionDetails {

    private final UUID transactionId;
    private final LocalDate transactionDate;
    private final LocalTime transactionTime;

    public TransactionDetails() {
        this.transactionId = UUID.randomUUID();
        this.transactionDate = java.time.LocalDate.now();
        this.transactionTime = java.time.LocalTime.now();
    }

    public TransactionDetails(UUID transactionId, LocalDate transactionDate, LocalTime transactionTime) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
    }
}
