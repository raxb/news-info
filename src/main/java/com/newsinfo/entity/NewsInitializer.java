package com.newsinfo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity for CRUD operations
 */
@Getter
@Setter
@Entity(name = "NewsInitializer")
@Table(name = "NEWS_INITIALIZER")
public class NewsInitializer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;
    private String transactionId;
    private String topic;
    private String location;
    private String reporterId;
    private String images;
    private String transactionDate;
    private String transactionTime;
    private boolean isUpdated;

    @OneToOne(mappedBy = "newsInitializer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private EndorsersFeed endorsersFeed;

}
