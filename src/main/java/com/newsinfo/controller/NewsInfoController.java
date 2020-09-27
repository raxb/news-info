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

@RestController
@RequestMapping("/v1/newsinfo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NewsInfoController {

    private final NewsFeederService newsFeederService;

    @PostMapping(path = "/posttopic", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsResponse> submitNewsTopic(@RequestBody NewsRequest newsRequest) {
        NewsResponse newsResponse = newsFeederService.createNewTopicWithDetails(newsRequest);
        return ResponseEntity.ok(newsResponse);
    }
}
