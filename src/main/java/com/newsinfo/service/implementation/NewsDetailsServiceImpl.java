package com.newsinfo.service.implementation;

import com.newsinfo.dto.NewsInitializerDAO;
import com.newsinfo.entity.EndorsersFeed;
import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.model.NewsRequest;
import com.newsinfo.repository.NewsInitializerRepository;
import com.newsinfo.service.EndorsersFeederService;
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
    private final EndorsersFeederService endorsersFeederService;

    /**
     * Persist to the defined Entity
     *
     * @param newsInitializerDAO object for data-store
     */
    @Override
    public void saveNews(NewsInitializerDAO newsInitializerDAO) {
        NewsInitializer newsInitializer = populateNewsInitializerEntity(newsInitializerDAO);
        newsInitializerRepository.save(newsInitializer);

        long newsId = newsInitializer.getNewsId();
        String transactionId = newsInitializer.getTransactionId();
        EndorsersFeed associatedEndorserFeed = endorsersFeederService.createEndorserFeedEntry(newsId,
                transactionId);

        newsInitializer.setEndorsersFeed(associatedEndorserFeed);

        newsInitializerRepository.flush();
    }

    /**
     * Mapper from DTO to Entity
     *
     * @param newsInitializerDAO object for data-store
     * @return persistent Entity
     */
    private NewsInitializer populateNewsInitializerEntity(NewsInitializerDAO newsInitializerDAO) {
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
    public NewsInitializer updateNews(NewsInitializer actualNews, NewsRequest modifiedNewsRequest) {
        isNewsModifiable(actualNews);

        actualNews.setTopic(modifiedNewsRequest.getTopic());
        actualNews.setLocation(modifiedNewsRequest.getEventLocation());
        actualNews.setReporterId(modifiedNewsRequest.getReporterId());
        actualNews.setImages(modifiedNewsRequest.getImages());
        actualNews.setUpdated(true);
        return newsInitializerRepository.save(actualNews);
    }

    private void isNewsModifiable(NewsInitializer actualNews) {
        LocalDate reportedDate = LocalDate.parse(actualNews.getTransactionDate());
        LocalTime reportedTime = LocalTime.parse(actualNews.getTransactionTime());
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        if (actualNews.isUpdated() || (reportedDate.isBefore(currentDate) || Duration.between(reportedTime,
                currentTime).toMinutes() > 15)) throw new RuntimeException("News already updated or updation time has" +
                " lapsed");
    }

}
