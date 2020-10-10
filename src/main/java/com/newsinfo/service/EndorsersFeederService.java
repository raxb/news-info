package com.newsinfo.service;

import com.newsinfo.model.endorser.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface EndorsersFeederService {

    String registerEndorser(RegisterRequest registerRequest);

    String getEndorsersFeed();

    void voteForNews(String newsId);
}
