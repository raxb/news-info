package com.newsinfo.model;

import lombok.Data;

/**
 * Facilitates the scope of Objects throughout the application
 */
@Data
public class NewsContext {

    private NewsRequest newsRequest;
    private TransactionDetails transactionDetails;
    private int endorsers;
}
