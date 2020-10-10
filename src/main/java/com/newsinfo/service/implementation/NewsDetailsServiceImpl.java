package com.newsinfo.service.implementation;

import com.newsinfo.dto.NewsInitializerDAO;
import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.model.NewsRequest;
import com.newsinfo.repository.NewsInitializerRepository;
import com.newsinfo.service.NewsDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class handling low-level implementations and processings
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NewsDetailsServiceImpl implements NewsDetailsService {

    private final NewsInitializerRepository newsInitializerRepository;
    private final EndorserFeederServiceImpl endorserFeederServiceImpl;

    /**
     * Persist to the defined Entity
     *
     * @param newsInitializerDAO object for data-store
     * @return transactionId for reference
     */
    @Override
    public String saveNews(NewsInitializerDAO newsInitializerDAO) {
        NewsInitializer newsInitializer = populateNewsDetailsEntity(newsInitializerDAO);

        newsInitializerRepository.save(newsInitializer);
        endorserFeederServiceImpl.createEndorserEntry(newsInitializer, newsInitializerDAO);
        newsInitializerRepository.flush();
        return newsInitializer.getTransactionId();
    }

    /**
     * Mapper from DTO to Entity
     *
     * @param newsInitializerDAO object for data-store
     * @return persistent Entity
     */
    private NewsInitializer populateNewsDetailsEntity(NewsInitializerDAO newsInitializerDAO) {
        NewsInitializer newsInitializer = new NewsInitializer();
        newsInitializer.setTransactionId(newsInitializerDAO.getTransactionId());
        newsInitializer.setTopic(newsInitializerDAO.getTopic());
        newsInitializer.setLocation(newsInitializerDAO.getLocation());
        newsInitializer.setReporterId(newsInitializerDAO.getReporterId());
        newsInitializer.setImages(newsInitializerDAO.getImages());
        newsInitializer.setTransactionDate(newsInitializerDAO.getTransactionDate());
        newsInitializer.setTransactionTime(newsInitializerDAO.getTransactionTime());
        return newsInitializer;
    }

    @Override
    public NewsInitializer updateNews(NewsInitializer newsTopic, NewsRequest modifiedNewsRequest) {
        isNewsModifiable(newsTopic);

        newsTopic.setTopic(modifiedNewsRequest.getTopic());
        newsTopic.setLocation(modifiedNewsRequest.getEventLocation());
        newsTopic.setReporterId(modifiedNewsRequest.getNewsInfoIdentifier());
        newsTopic.setImages(modifiedNewsRequest.getImages());
        newsTopic.setUpdated(true);
        return newsInitializerRepository.save(newsTopic);
    }

    private void isNewsModifiable(NewsInitializer newsTopic) {
        LocalDate reportedDate = LocalDate.parse(newsTopic.getTransactionDate());
        LocalTime reportedTime = LocalTime.parse(newsTopic.getTransactionTime());
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        if (newsTopic.isUpdated() || (reportedDate.isBefore(currentDate) || Duration.between(reportedTime,
                currentTime).toMinutes() > 15)) throw new RuntimeException();
    }

}
