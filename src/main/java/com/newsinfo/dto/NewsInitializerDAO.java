package com.newsinfo.dto;

import lombok.Data;

/**
 * An intermediary between the Domain-Entity and Request Object
 */
@Data
public class NewsInitializerDAO {
    private long newsId;
    private String transactionId;
    private String transactionDate;
    private String transactionTime;
    private String topic;
    private String location;
    private String reporterId;
    private String images;
    private boolean isUpdated;
}
