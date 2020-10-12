package com.newsinfo.service;

import com.newsinfo.entity.EndorsersFeed;
import com.newsinfo.model.endorser.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface EndorsersFeederService {
    EndorsersFeed createEndorserFeedEntry(long newsId, String transactionId);

    String registerEndorser(RegisterRequest registerRequest);

    String getEndorsersFeed();

    void voteForNews(String endorserId, String newsId);
}
