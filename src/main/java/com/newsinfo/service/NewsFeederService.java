package com.newsinfo.service;

import com.newsinfo.model.NewsContext;
import com.newsinfo.model.NewsRequest;
import com.newsinfo.model.NewsResponse;
import com.newsinfo.model.TransactionDetails;
import org.springframework.stereotype.Component;

@Component
public class NewsFeederService {

    public NewsResponse createNewTopicWithDetails(NewsRequest newsRequest) {
        NewsContext context = new NewsContext();
        TransactionDetails transactionDetails = new TransactionDetails();
        NewsResponse newsResponse = new NewsResponse();

        context.setTransactionDetails(transactionDetails);
        context.setNewsRequest(newsRequest);

        publishNewsTopicOnQueue(newsRequest);

        newsResponse.setTransactionDetails(transactionDetails);
        newsResponse.setNewsRequest(newsRequest);

        return newsResponse;
    }

    private void publishNewsTopicOnQueue(NewsRequest newsRequest) {
    }

}
