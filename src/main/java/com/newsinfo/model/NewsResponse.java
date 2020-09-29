package com.newsinfo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Response definition
 */
@Data
public class NewsResponse implements Serializable {

    private TransactionDetails transactionDetails;
    private NewsRequest newsRequest;
}
