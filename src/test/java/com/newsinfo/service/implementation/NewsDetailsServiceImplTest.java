package com.newsinfo.service.implementation;

import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.model.NewsRequest;
import com.newsinfo.model.TransactionDetails;
import com.newsinfo.repository.NewsInitializerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class NewsDetailsServiceImplTest {

    private NewsRequest newsRequest;
    private NewsInitializer newsInitializer;
    private TransactionDetails transactionDetails;

    @InjectMocks
    private NewsDetailsServiceImpl newsDetailsServiceImpl;

    @Mock
    private NewsInitializerRepository newsInitializerRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        newsRequest = new NewsRequest();
        newsRequest.setTopic("Bruce Wayne is Batman");
        newsRequest.setEventLocation("Gotham");
        newsRequest.setNewsInfoIdentifier("KentC");
        newsRequest.setImages("None");

        newsInitializer = new NewsInitializer();
        newsInitializer.setId(1);
        newsInitializer.setTopic("Bruce Wayne is Batman");
        newsInitializer.setLocation("Gotham");
        newsInitializer.setReporterId("KentC");
        newsInitializer.setImages("None");
        newsInitializer.setUpdated(false);
        newsInitializer.setTransactionId("937c3c5f-7d9e-4d25-bd71-c40447d3eec7");
    }

    @Test
    void saveNews() {
    }

    @Test
    void testUpdateNews() {
        newsInitializer.setTransactionDate(LocalDate.now().toString());
        newsInitializer.setTransactionTime(LocalTime.now().toString());
        newsDetailsServiceImpl.updateNews(newsInitializer, newsRequest);
        assertTrue(newsInitializer.isUpdated());
    }

    @Test
    void testUpdateNewsAlreadyUpdated() {
        newsInitializer.setTransactionDate(LocalDate.now().toString());
        newsInitializer.setTransactionTime(LocalTime.now().toString());
        newsInitializer.setUpdated(true);
        assertThrows(RuntimeException.class, () -> newsDetailsServiceImpl.updateNews(newsInitializer, newsRequest));
    }

    @Test
    void testUpdateNewsTimeLapsed15Minutes() {
        newsInitializer.setTransactionDate(LocalDate.now().toString());
        newsInitializer.setTransactionTime(LocalTime.now().minusMinutes(15 + new Random().nextInt(60)).toString());
        assertThrows(RuntimeException.class, () -> newsDetailsServiceImpl.updateNews(newsInitializer, newsRequest));
    }

    @Test
    void testUpdateNewsDayLapsed() {
        newsInitializer.setTransactionDate(LocalDate.now().minusDays(new Random().nextInt(31)).toString());
        newsInitializer.setTransactionTime(LocalTime.now().minusMinutes(new Random().nextInt(60)).toString());
        assertThrows(RuntimeException.class, () -> newsDetailsServiceImpl.updateNews(newsInitializer, newsRequest));
    }
}