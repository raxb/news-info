package com.newsinfo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.repository.NewsInitializerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EndorsersFeederService {

    private final NewsInitializerRepository newsInitializerRepository;

    public String getEndorsersFeed() {
        List<NewsInitializer> feeds = newsInitializerRepository.findAllSortedByNewsPosted();
        String stringFeeds = null;
        try {
            stringFeeds = new ObjectMapper().writeValueAsString(feeds);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return stringFeeds;
    }
}
