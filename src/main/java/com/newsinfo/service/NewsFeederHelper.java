package com.newsinfo.service;

import com.newsinfo.aspect.logging.Logged;
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

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
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
    @Logged
    public Map<String, Object> createNewTopicWithDetails(NewsRequest newsRequest) {
        this.newsRequest = newsRequest;
        transactionDetails = new TransactionDetails();
        Map<String, Object> newsInitializerWithTransactions = new HashMap<>();

        populateNewsDetailsDAOWithRequest();
        NewsInitializer newsInitializer = newsDetailsServiceImpl.saveNews(newsInitializerDAO);

        newsInitializerWithTransactions.put("newsInitializer", newsInitializer);
        newsInitializerWithTransactions.put("transactionDetails", transactionDetails);
        return newsInitializerWithTransactions;
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
        newsInitializerDAO.setImages(newsRequest.getImages());
        newsInitializerDAO.setUpdated(false);
    }

    @Logged
    public Map<String, Object> updateRequest(NewsRequest newsRequestUpdate, String reporterId, String newsId) {
        NewsInitializer actualNews = newsInitializerRepository.findById(Long.valueOf(newsId)).orElseThrow(() -> new EntityNotFoundException(newsId));
        Map<String, Object> newsInitializerWithTransactions = new HashMap<>();

        if (reporterId.equals(actualNews.getReporterProfile().getId()))
            isNewsModifiable(actualNews);
        else
            throw new RuntimeException("You are not allowed to update");

        NewsInitializer updatedEntity = newsDetailsServiceImpl.updateNews(actualNews, newsRequestUpdate);

        TransactionDetails transactionDetails = new TransactionDetails(
                UUID.fromString(updatedEntity.getTransactionId()),
                LocalDate.parse(updatedEntity.getTransactionDate()),
                LocalTime.parse(updatedEntity.getTransactionTime()));
        newsInitializerWithTransactions.put("newsInitializer", updatedEntity);
        newsInitializerWithTransactions.put("transactionDetails", transactionDetails);
        return newsInitializerWithTransactions;
    }

    public NewsResponse generateResponse(TransactionDetails generatedTransactions, NewsRequest newsRequested,
                                         long newsId) {
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setNewsId(String.valueOf(newsId));
        newsResponse.setTransactionDetails(generatedTransactions);
        newsResponse.setNewsRequest(newsRequested);
        return newsResponse;
    }

    @Logged
    public void isNewsModifiable(NewsInitializer actualNews) {
        LocalDate reportedDate = LocalDate.parse(actualNews.getTransactionDate());
        LocalTime reportedTime = LocalTime.parse(actualNews.getTransactionTime());
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        if (actualNews.isUpdated() || (reportedDate.isBefore(currentDate) || Duration.between(reportedTime,
                currentTime).toMinutes() > 15)) throw new RuntimeException("News already updated or updation time has" +
                " lapsed");
    }
}
