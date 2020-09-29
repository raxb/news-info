package com.newsinfo.controller;

import com.newsinfo.model.NewsRequest;
import com.newsinfo.model.NewsResponse;
import com.newsinfo.service.NewsFeederService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class handling requests and responses from the Client
 */
@RestController
@RequestMapping("/v1/newsinfo")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NewsInfoController {

    private final NewsFeederService newsFeederService;

    /**
     * Invoked when a reporter needs to report a News
     *
     * @param newsRequest schema for the News
     * @return entity facilitating body and headers
     */
    @PostMapping(path = "/posttopic", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsResponse> submitNewsTopic(@RequestBody NewsRequest newsRequest) {
        NewsResponse newsResponse = newsFeederService.createNewTopicWithDetails(newsRequest);
        return ResponseEntity.ok(newsResponse);
    }
}
