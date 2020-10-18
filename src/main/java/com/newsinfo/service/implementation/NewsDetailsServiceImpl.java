package com.newsinfo.service.implementation;

import com.newsinfo.aspect.logging.Logged;
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

/**
 * Class handling low-level implementations and processings
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NewsDetailsServiceImpl implements NewsDetailsService {

    private final NewsInitializerRepository newsInitializerRepository;
    private final EndorsersFeederService endorsersFeederService;

    @Override
    @Logged
    public NewsInitializer saveNews(NewsInitializerDAO newsInitializerDAO) {
        NewsInitializer newsInitializer = populateNewsInitializerEntity(newsInitializerDAO);
        newsInitializerRepository.save(newsInitializer);

        long newsId = newsInitializer.getNewsId();
        String transactionId = newsInitializer.getTransactionId();
        EndorsersFeed associatedEndorserFeed = endorsersFeederService.createEndorserFeedEntry(newsId, transactionId);

        newsInitializer.setEndorsersFeed(associatedEndorserFeed);

        newsInitializerRepository.flush();
        return newsInitializer;
    }

    private NewsInitializer populateNewsInitializerEntity(NewsInitializerDAO newsInitializerDAO) {
        NewsInitializer newsInitializer = new NewsInitializer();
        newsInitializer.setTransactionId(newsInitializerDAO.getTransactionId());
        newsInitializer.setTopic(newsInitializerDAO.getTopic());
        newsInitializer.setLocation(newsInitializerDAO.getLocation());
        newsInitializer.setImages(newsInitializerDAO.getImages());
        newsInitializer.setTransactionDate(newsInitializerDAO.getTransactionDate());
        newsInitializer.setTransactionTime(newsInitializerDAO.getTransactionTime());
        return newsInitializer;
    }

    @Override
    @Logged
    public NewsInitializer updateNews(NewsInitializer actualNews, NewsRequest modifiedNewsRequest) {
        actualNews.setTopic(modifiedNewsRequest.getTopic());
        actualNews.setLocation(modifiedNewsRequest.getEventLocation());
        actualNews.setImages(modifiedNewsRequest.getImages());
        actualNews.setUpdated(true);
        return newsInitializerRepository.save(actualNews);
    }

}
