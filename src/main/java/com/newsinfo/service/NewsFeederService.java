package com.newsinfo.service;

import com.newsinfo.dto.NewsDetailsDTO;
import com.newsinfo.model.NewsContext;
import com.newsinfo.model.NewsRequest;
import com.newsinfo.model.NewsResponse;
import com.newsinfo.model.TransactionDetails;
import com.newsinfo.service.implementation.NewsDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Entry Service for building up the request, processing and persisting delegation
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NewsFeederService {

    private final NewsDetailsServiceImpl newsDetailsServiceImpl;
    private NewsDetailsDTO newsDetailsDTO;
    private TransactionDetails transactionDetails;

    /**
     * Facilitates for News building from the primitive request
     *
     * @param newsRequest client request
     * @return formatted response
     */
    public NewsResponse createNewTopicWithDetails(NewsRequest newsRequest) {
        NewsContext context = new NewsContext();
        NewsResponse newsResponse = new NewsResponse();
        transactionDetails = new TransactionDetails();

        context.setTransactionDetails(transactionDetails);
        context.setNewsRequest(newsRequest);

        populateNewsDetailsDTOWithRequest(newsRequest);
        newsDetailsServiceImpl.saveNews(newsDetailsDTO);

        newsResponse.setTransactionDetails(transactionDetails);
        newsResponse.setNewsRequest(newsRequest);
        return newsResponse;
    }

    /**
     * Mapping data-store
     *
     * @param newsRequest client request
     */
    private void populateNewsDetailsDTOWithRequest(NewsRequest newsRequest) {
        newsDetailsDTO = new NewsDetailsDTO();
        newsDetailsDTO.setTransactionId(transactionDetails.getTransactionId().toString());
        newsDetailsDTO.setTransactionTimestamp(transactionDetails.getTransactionDate().toString());
        newsDetailsDTO.setTopic(newsRequest.getTopic());
        newsDetailsDTO.setLocation(newsRequest.getEventLocation());
        newsDetailsDTO.setReporterId(newsRequest.getNewsInfoIdentifier());
        newsDetailsDTO.setImages(newsRequest.getImages());
    }
}
