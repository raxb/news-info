package com.newsinfo.service;

import com.newsinfo.dto.NewsDetailsDTO;

/**
 * Define generic functionality
 */
public interface NewsDetailsService {
    String saveNews(NewsDetailsDTO newsDetailsDTO);
}