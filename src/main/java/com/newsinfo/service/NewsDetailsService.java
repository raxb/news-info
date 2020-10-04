package com.newsinfo.service;

import com.newsinfo.dto.NewsInitializerDTO;
import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.model.NewsRequest;

/**
 * Define generic functionality
 */
public interface NewsDetailsService {
    String saveNews(NewsInitializerDTO newsInitializerDTO);

    NewsInitializer updateNews(NewsInitializer newsInitializer, NewsRequest modifiedNewsRequest);
}