package com.newsinfo.controller;

import com.newsinfo.model.NewsRequest;
import com.newsinfo.model.NewsResponse;
import com.newsinfo.service.EndorsersFeederService;
import com.newsinfo.service.NewsFeederService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class handling requests and responses from the Client
 */
@RestController
@RequestMapping(path = "/v1/newsinfo", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NewsInfoController {

    private final NewsFeederService newsFeederService;
    private final EndorsersFeederService endorsersFeederService;

    /**
     * Invoked when a reporter needs to report a News
     *
     * @param newsRequest schema for the News
     * @return entity facilitating body and headers
     */
    @PostMapping("/posttopic")
    public ResponseEntity<NewsResponse> submitNewsTopic(@RequestBody NewsRequest newsRequest) {
        newsFeederService.createNewTopicWithDetails(newsRequest);
        NewsResponse newsResponse = newsFeederService.generateResponse();
        return ResponseEntity.ok(newsResponse);
    }

    /**
     * Put method for updating within 15 minutes the NewsTopic that was posted (one-time modification)
     */
    @PutMapping("/updatetopic/{transactionId}")
    public ResponseEntity<NewsResponse> updateNewsRequest(@RequestBody NewsRequest newsRequest,
                                                          @PathVariable String transactionId) {
        NewsResponse newsResponse = newsFeederService.updateRequest(newsRequest, transactionId);
        return ResponseEntity.ok(newsResponse);
    }

    /**
     * Post method for Endorser to poll on the NewsTopic and redirect call to Endorser profile, for recording the
     * NewsTopic and their poll and internally call to persist Endorsers List based on time to get initial 100 endorsers
     * for rewarding
     */
    @PutMapping("/pollForNews/{newsId}")
    public ResponseEntity<String> castVoteForNews(@PathVariable String newsId) {
        //endorsersFeederService.voteForNews(newsId);
        return null;
    }

    /**
     * Get Method for fetching the Endorser News Feeds information with NewsTopic that were polled Generic to display
     * NewsTopic and the current number of endorsers
     */
    @GetMapping("/allEndorsersFeed")
    public ResponseEntity<String> fetchEndorsersFeedData() {
        String feedData = endorsersFeederService.getEndorsersFeed();
        return ResponseEntity.ok(feedData);
    }


}
