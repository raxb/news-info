package com.newsinfo.service.implementation;

import com.newsinfo.dto.NewsInitializerDAO;
import com.newsinfo.entity.EndorsersFeed;
import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.initializer.InitializerApplication;
import com.newsinfo.model.NewsRequest;
import com.newsinfo.service.NewsFeederHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = InitializerApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class NewsDetailsServiceImplTest {

    private NewsInitializer newsInitializer;
    private EndorsersFeed endorsersFeed;

    @Autowired
    private NewsDetailsServiceImpl newsDetailsService;

    @InjectMocks
    private NewsFeederHelper newsFeederHelper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setTopic("Bruce Wayne is Batman");
        newsRequest.setEventLocation("Gotham");
        newsRequest.setImages("None");

        endorsersFeed = new EndorsersFeed();
        endorsersFeed.setPollCount(0);
        endorsersFeed.setTransactionId("937c3c5f-7d9e-4d25-bd71-c40447d3eec7");
        newsInitializer = new NewsInitializer();
        newsInitializer.setTopic("Bruce Wayne is Batman");
        newsInitializer.setLocation("Gotham");
        newsInitializer.setImages("None");
        newsInitializer.setUpdated(false);
        newsInitializer.setTransactionId("937c3c5f-7d9e-4d25-bd71-c40447d3eec7");
        newsInitializer.setTransactionTime("07:17:52.341");
        newsInitializer.setTransactionDate("2020-10-24");
        newsInitializer.setEndorsersFeed(endorsersFeed);
    }

    @Test
    void testSaveNews() {
        NewsInitializerDAO testNewsInitializerDAO = new NewsInitializerDAO();
        testNewsInitializerDAO.setTransactionId(newsInitializer.getTransactionId());
        testNewsInitializerDAO.setTransactionDate(newsInitializer.getTransactionDate());
        testNewsInitializerDAO.setTransactionTime(newsInitializer.getTransactionTime());
        testNewsInitializerDAO.setTopic(newsInitializer.getTopic());
        testNewsInitializerDAO.setLocation(newsInitializer.getLocation());
        testNewsInitializerDAO.setImages(newsInitializer.getImages());
        testNewsInitializerDAO.setUpdated(newsInitializer.isUpdated());

        NewsInitializer savedNews = newsDetailsService.saveNews(testNewsInitializerDAO);
        newsInitializer.setNewsId(savedNews.getNewsId());
        newsInitializer.getEndorsersFeed().setNewsId(savedNews.getEndorsersFeed().getNewsId());
        assertNotNull(savedNews);
        assertEquals(newsInitializer, savedNews);
    }

    @Test
    void testUpdateNewsAlreadyUpdated() {
        newsInitializer.setTransactionDate(LocalDate.now().toString());
        newsInitializer.setTransactionTime(LocalTime.now().toString());
        newsInitializer.setUpdated(true);
        assertThrows(RuntimeException.class, () -> newsFeederHelper.isNewsModifiable(newsInitializer));
    }

    @Test
    void testUpdateNewsTimeLapsed15Minutes() {
        newsInitializer.setTransactionDate(LocalDate.now().toString());
        newsInitializer.setTransactionTime(LocalTime.now().minusMinutes(15 + new Random().nextInt(60)).toString());
        assertThrows(RuntimeException.class, () -> newsFeederHelper.isNewsModifiable(newsInitializer));
    }

    @Test
    void testUpdateNewsDayLapsed() {
        newsInitializer.setTransactionDate(LocalDate.now().minusDays(new Random().nextInt(31)).toString());
        newsInitializer.setTransactionTime(LocalTime.now().minusMinutes(new Random().nextInt(60)).toString());
        assertThrows(RuntimeException.class, () -> newsFeederHelper.isNewsModifiable(newsInitializer));
    }

    @Test
    void testUpdateNews() {
        NewsRequest newsUpdate = new NewsRequest();
        newsUpdate.setTopic("Bruce Wayne is Billionaire");
        newsUpdate.setEventLocation("Gotham");
        newsUpdate.setImages("None");
        newsInitializer.setNewsId(1L);
        endorsersFeed.setNewsId(1L);
        NewsInitializer updatedNews = newsDetailsService.updateNews(newsInitializer, newsUpdate);
        assertNotNull(updatedNews);
        assertTrue(updatedNews.isUpdated());
    }
}