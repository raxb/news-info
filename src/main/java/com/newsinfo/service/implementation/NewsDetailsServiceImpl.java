package com.newsinfo.service.implementation;

import com.newsinfo.dto.NewsInitializerDTO;
import com.newsinfo.entity.EndorsersFeed;
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

    /**
     * Persist to the defined Entity
     *
     * @param newsInitializerDTO object for data-store
     * @return transactionId for reference
     */
    @Override
    public String saveNews(NewsInitializerDTO newsInitializerDTO) {
        NewsInitializer newsInitializer = populateNewsDetailsEntity(newsInitializerDTO);

        return newsInitializerRepository.save(newsInitializer).getTransactionId();
    }

    /**
     * Mapper from DTO to Entity
     *
     * @param newsInitializerDTO object for data-store
     * @return persistent Entity
     */
    private NewsInitializer populateNewsDetailsEntity(NewsInitializerDTO newsInitializerDTO) {
        NewsInitializer newsInitializer = new NewsInitializer();
        newsInitializer.setTransactionId(newsInitializerDTO.getTransactionId());
        newsInitializer.setTopic(newsInitializerDTO.getTopic());
        newsInitializer.setLocation(newsInitializerDTO.getLocation());
        newsInitializer.setReporterId(newsInitializerDTO.getReporterId());
        newsInitializer.setImages(newsInitializerDTO.getImages());
        newsInitializer.setTransactionDate(newsInitializerDTO.getTransactionDate());
        newsInitializer.setTransactionTime(newsInitializerDTO.getTransactionTime());
        createEndorserEntry(newsInitializer);
        return newsInitializer;
    }

    private void createEndorserEntry(NewsInitializer newsInitializer) {
        EndorsersFeed endorsersFeed = new EndorsersFeed();
        endorsersFeed.setNewsInitializer(newsInitializer);
        endorsersFeed.setPollCount(0);
        newsInitializer.setEndorsersFeed(endorsersFeed);
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
