package com.newsinfo.dto;

import lombok.Data;

/**
 * An intermediary between the Domain-Entity and Request Object
 */
@Data
public class NewsDetailsDTO {
    private long id;
    private String transactionId;
    private String transactionTimestamp;
    private String topic;
    private String location;
    private String reporterId;
    private String images;
}
