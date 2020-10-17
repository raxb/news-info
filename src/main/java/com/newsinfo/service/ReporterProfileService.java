package com.newsinfo.service;


import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.model.endorser.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface ReporterProfileService {

    String registerReporter(RegisterRequest registerRequest);

    void addReportedNews(String reporterId, NewsInitializer newsInitializer);
}
