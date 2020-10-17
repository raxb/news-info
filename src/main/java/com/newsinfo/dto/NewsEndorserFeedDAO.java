package com.newsinfo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsEndorserFeedDAO {
    private long newsId;
    private String images;
    private String location;
    private String topic;
    private String transactionDate;
    private String transactionTime;
    private int pollCount;

    public NewsEndorserFeedDAO(long newsId, String images, String location, String topic, String transactionDate,
                               String transactionTime, int pollCount) {
        this.newsId = newsId;
        this.images = images;
        this.location = location;
        this.topic = topic;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.pollCount = pollCount;
    }
}
