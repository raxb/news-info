package com.newsinfo.model;

import lombok.Data;

@Data
public class NewsContext {

    private NewsRequest newsRequest;
    private TransactionDetails transactionDetails;
    private int endorsers;
}
