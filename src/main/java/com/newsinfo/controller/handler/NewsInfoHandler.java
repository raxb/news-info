package com.newsinfo.controller.handler;

import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.model.NewsRequest;
import com.newsinfo.service.NewsFeederHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NewsInfoHandler {

    private final NewsFeederHelper newsFeederHelper;

    public Map<String, Object> submitNewsTopic(NewsRequest newsRequest) {
        return newsFeederHelper.createNewTopicWithDetails(newsRequest);
    }

    public Map<String, Object> updateNewsRequest(NewsRequest newsRequest, String reporterId, String newsId) {
        return newsFeederHelper.updateRequest(newsRequest, reporterId, newsId);
    }

    public NewsInitializer deleteNews(String reporterId, String newsId) {
        return newsFeederHelper.deleteRequest(reporterId, newsId);
    }
}
