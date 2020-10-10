package com.newsinfo.service;

import com.newsinfo.dto.NewsInitializerDAO;
import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.model.NewsRequest;

/**
 * Define generic functionality
 */
public interface NewsDetailsService {
    String saveNews(NewsInitializerDAO newsInitializerDAO);

    NewsInitializer updateNews(NewsInitializer newsInitializer, NewsRequest modifiedNewsRequest);
}