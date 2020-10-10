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
public class NewsFeederService {

    private final NewsDetailsServiceImpl newsDetailsServiceImpl;
    private final NewsInitializerRepository newsInitializerRepository;
    private NewsInitializerDAO newsInitializerDAO;
    private TransactionDetails transactionDetails;
    private NewsRequest newsRequest;
    private NewsResponse newsResponse;

    /**
     * Facilitates for News building from the primitive request
     *
     * @param newsRequest client request
     */
    public void createNewTopicWithDetails(NewsRequest newsRequest) {
        this.newsRequest = newsRequest;
        transactionDetails = new TransactionDetails();

        populateNewsDetailsDTOWithRequest(newsRequest);
        newsDetailsServiceImpl.saveNews(newsInitializerDAO);
    }

    /**
     * Mapping data-store
     *
     * @param newsRequest client request
     */
    private void populateNewsDetailsDTOWithRequest(NewsRequest newsRequest) {
        newsInitializerDAO = new NewsInitializerDAO();
        newsInitializerDAO.setTransactionId(transactionDetails.getTransactionId().toString());
        newsInitializerDAO.setTransactionDate(transactionDetails.getTransactionDate().toString());
        newsInitializerDAO.setTransactionTime(transactionDetails.getTransactionTime().toString());
        newsInitializerDAO.setTopic(newsRequest.getTopic());
        newsInitializerDAO.setLocation(newsRequest.getEventLocation());
        newsInitializerDAO.setReporterId(newsRequest.getNewsInfoIdentifier());
        newsInitializerDAO.setImages(newsRequest.getImages());
        newsInitializerDAO.setUpdated(false);
    }

    public NewsResponse generateResponse() {
        newsResponse = new NewsResponse();
        newsResponse.setTransactionDetails(transactionDetails);
        newsResponse.setNewsRequest(newsRequest);
        return newsResponse;
    }

    public NewsResponse updateRequest(NewsRequest newsRequest, String transactionId) {
        NewsInitializer newsTopic = newsInitializerRepository.findByTransactionId(transactionId);
        NewsInitializer updatedEntity = newsDetailsServiceImpl.updateNews(newsTopic, newsRequest);

        newsResponse = new NewsResponse();
        transactionDetails = new TransactionDetails(
                UUID.fromString(updatedEntity.getTransactionId()),
                LocalDate.parse(updatedEntity.getTransactionDate()),
                LocalTime.parse(updatedEntity.getTransactionTime()));
        newsResponse.setTransactionDetails(transactionDetails);
        newsResponse.setNewsRequest(newsRequest);
        return newsResponse;
    }
}
