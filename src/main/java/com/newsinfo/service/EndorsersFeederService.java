package com.newsinfo.service;

import com.newsinfo.entity.EndorsersFeed;
import org.springframework.stereotype.Service;

@Service
public interface EndorsersFeederService {
    EndorsersFeed createEndorserFeedEntry(long newsId, String transactionId);

    String getEndorsersFeed();

    void voteForNews(String endorserId, String newsId);
}
