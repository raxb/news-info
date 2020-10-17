package com.newsinfo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Response definition
 */
@Data
public class NewsResponse implements Serializable {

    private String newsId;
    private TransactionDetails transactionDetails;
    private NewsRequest newsRequest;
}
