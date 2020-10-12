package com.newsinfo.controller;

import com.newsinfo.model.NewsRequest;
import com.newsinfo.model.NewsResponse;
import com.newsinfo.service.NewsFeederHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

/**
 * Class handling requests and responses from the Client
 */
@RestController
@RequestMapping(path = "/v1/newsinfo", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NewsInfoController {

    private final NewsFeederHelper newsFeederHelper;

    /**
     * Invoked when a reporter needs to report a News
     *
     * @param newsRequest schema for the News
     * @return entity facilitating body and headers
     */
    @PostMapping("/posttopic")
    public ResponseEntity<NewsResponse> submitNewsTopic(@RequestBody NewsRequest newsRequest) {
        NewsResponse newsResponse = newsFeederHelper.createNewTopicWithDetails(newsRequest);
        return ResponseEntity.ok(newsResponse);
    }

    /**
     * Put method for updating within 15 minutes the NewsTopic that was posted (one-time modification)
     */
    @PutMapping("/updatetopic/{transactionId}")
    public ResponseEntity<NewsResponse> updateNewsRequest(@RequestBody NewsRequest newsRequest,
                                                          @PathVariable String transactionId) {
        NewsResponse newsResponse = newsFeederHelper.updateRequest(newsRequest, transactionId);
        return ResponseEntity.ok(newsResponse);
    }

    @DeleteMapping("/{reporterId}/deleteNews/{transactionId}")
    public Response<String> deleteNews(@PathVariable String reporterId, @PathVariable String transactionId) {

        return null;
    }
}
