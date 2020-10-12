package com.newsinfo.service;

import com.newsinfo.dto.NewsInitializerDAO;
import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.model.NewsRequest;
import com.newsinfo.model.NewsResponse;
import com.newsinfo.model.TransactionDetails;
import com.newsinfo.repository.NewsInitializerRepository;
import com.newsinfo.service.implementation.NewsDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Entry Service for building up the request, processing and persisting delegation
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NewsFeederHelper {

    private final NewsDetailsServiceImpl newsDetailsServiceImpl;
    private final NewsInitializerRepository newsInitializerRepository;
    private NewsInitializerDAO newsInitializerDAO;
    private TransactionDetails transactionDetails;
    private NewsRequest newsRequest;

    /**
     * Facilitates for News building from the primitive request
     *
     * @param newsRequest client request
     */
    public NewsResponse createNewTopicWithDetails(NewsRequest newsRequest) {
        this.newsRequest = newsRequest;
        transactionDetails = new TransactionDetails();

        populateNewsDetailsDAOWithRequest();
        newsDetailsServiceImpl.saveNews(newsInitializerDAO);
        return generateResponse(transactionDetails, newsRequest);
    }

    /**
     * Mapping data-store
     */
    private void populateNewsDetailsDAOWithRequest() {
        newsInitializerDAO = new NewsInitializerDAO();
        newsInitializerDAO.setTransactionId(transactionDetails.getTransactionId().toString());
        newsInitializerDAO.setTransactionDate(transactionDetails.getTransactionDate().toString());
        newsInitializerDAO.setTransactionTime(transactionDetails.getTransactionTime().toString());
        newsInitializerDAO.setTopic(newsRequest.getTopic());
        newsInitializerDAO.setLocation(newsRequest.getEventLocation());
        newsInitializerDAO.setReporterId(newsRequest.getReporterId());
        newsInitializerDAO.setImages(newsRequest.getImages());
        newsInitializerDAO.setUpdated(false);
    }

    public NewsResponse updateRequest(NewsRequest newsRequestUpdate, String transactionId) {
        NewsInitializer actualNews = newsInitializerRepository.findByTransactionId(transactionId);
        NewsInitializer updatedEntity = newsDetailsServiceImpl.updateNews(actualNews, newsRequestUpdate);

        TransactionDetails transactionDetails = new TransactionDetails(
                UUID.fromString(updatedEntity.getTransactionId()),
                LocalDate.parse(updatedEntity.getTransactionDate()),
                LocalTime.parse(updatedEntity.getTransactionTime()));
        return generateResponse(transactionDetails, newsRequestUpdate);
    }

    private NewsResponse generateResponse(TransactionDetails generatedTransactions, NewsRequest newsRequested) {
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setTransactionDetails(generatedTransactions);
        newsResponse.setNewsRequest(newsRequested);
        return newsResponse;
    }
}
