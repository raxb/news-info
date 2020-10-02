package com.newsinfo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * JPA Entity for CRUD operations
 */
@Entity
@Table(name = "NEWS_INITIALIZER")
@Data
public class NewsInitializer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String transactionId;
    private String topic;
    private String location;
    private String reporterId;
    private String images;
    private String transactionDate;
    private String transactionTime;
    private boolean isUpdated;
}
