package com.newsinfo.model;

import com.newsinfo.entity.NewsInitializer;
import lombok.Data;

/**
 * Facilitates the scope of Objects throughout the application
 */
@Data
public class NewsContext {

    private NewsInitializer newsInitializer;
    private TransactionDetails transactionDetails;
    private int endorsers;
}
