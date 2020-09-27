package com.newsinfo.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class TransactionDetails {

    private final UUID transactionId;
    private final LocalDateTime transactionDate;

    public TransactionDetails() {
        this.transactionId = UUID.randomUUID();
        this.transactionDate = java.time.LocalDateTime.now();
    }
}
