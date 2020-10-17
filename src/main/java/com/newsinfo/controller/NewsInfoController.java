package com.newsinfo.controller;

import com.newsinfo.model.NewsRequest;
import com.newsinfo.service.NewsFeederHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.Map;

/**
 * Class handling requests and responses from the Client
 */
@RestController
@RequestMapping(path = "/v1/newsinfo", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
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
