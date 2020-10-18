package com.newsinfo.controller;

import com.newsinfo.model.NewsRequest;
import com.newsinfo.service.NewsFeederHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.ws.Response;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NewsInfoController {

    private final NewsFeederHelper newsFeederHelper;

    public Map<String, Object> submitNewsTopic(NewsRequest newsRequest) {
        return newsFeederHelper.createNewTopicWithDetails(newsRequest);
    }

    public Map<String, Object> updateNewsRequest(NewsRequest newsRequest, String reporterId, String newsId) {
        return newsFeederHelper.updateRequest(newsRequest, reporterId, newsId);
    }

    @DeleteMapping("/{reporterId}/deleteNews/{transactionId}")
    public Response<String> deleteNews(@PathVariable String reporterId, @PathVariable String transactionId) {

        return null;
    }
}
