package com.newsinfo.controller;

import com.newsinfo.aspect.logging.Logged;
import com.newsinfo.controller.handler.NewsInfoHandler;
import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.model.NewsRequest;
import com.newsinfo.model.NewsResponse;
import com.newsinfo.model.TransactionDetails;
import com.newsinfo.model.endorser.RegisterRequest;
import com.newsinfo.service.NewsFeederHelper;
import com.newsinfo.service.ReporterProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/v1/reporter", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ReporterController {

    private final NewsInfoHandler newsInfoHandler;
    private final ReporterProfileService reporterProfileService;
    private final NewsFeederHelper newsFeederHelper;

    @PostMapping("/register")
    @Logged
    public String registerReporter(@RequestBody RegisterRequest registerRequest) {
        return reporterProfileService.registerReporter(registerRequest);
    }

    @PostMapping("/{reporterId}/reportNews")
    @Logged
    public ResponseEntity<NewsResponse> addNews(@PathVariable String reporterId, @RequestBody NewsRequest newsRequest) {
        Map<String, Object> newsInitializerWithTransactions = newsInfoHandler.submitNewsTopic(newsRequest);
        NewsInitializer newsInitializer = (NewsInitializer) newsInitializerWithTransactions.get("newsInitializer");
        TransactionDetails transactionDetails = (TransactionDetails) newsInitializerWithTransactions.get("transactionDetails");
        NewsResponse newsResponse = newsFeederHelper.generateResponse(transactionDetails, newsRequest,
                newsInitializer.getNewsId());
        reporterProfileService.addReportedNews(reporterId, newsInitializer);
        return ResponseEntity.ok(newsResponse);
    }

    @PutMapping("/{reporterId}/updateNews/{newsId}")
    @Logged
    public ResponseEntity<NewsResponse> updateNewsRequest(@RequestBody NewsRequest newsRequest,
                                                          @PathVariable String reporterId,
                                                          @PathVariable String newsId) {
        Map<String, Object> newsInitializerWithTransactions = newsInfoHandler.updateNewsRequest(newsRequest,
                reporterId, newsId);
        NewsInitializer newsInitializer = (NewsInitializer) newsInitializerWithTransactions.get("newsInitializer");
        TransactionDetails transactionDetails = (TransactionDetails) newsInitializerWithTransactions.get("transactionDetails");
        NewsResponse newsResponse = newsFeederHelper.generateResponse(transactionDetails, newsRequest,
                newsInitializer.getNewsId());
        return ResponseEntity.ok(newsResponse);
    }

    @DeleteMapping("/{reporterId}/deleteNews/{newsId}")
    @Logged
    public ResponseEntity<String> deleteNews(@PathVariable String reporterId, @PathVariable String newsId) {
        NewsInitializer newsForDeletion = newsInfoHandler.deleteNews(reporterId, newsId);
        reporterProfileService.deleteReportedNews(reporterId, newsForDeletion);
        return ResponseEntity.ok("News has been successfully deleted " + newsId);
    }
}
