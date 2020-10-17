package com.newsinfo.service.implementation;

import com.newsinfo.entity.EndorsersFeed;
import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.model.NewsRequest;
import com.newsinfo.model.TransactionDetails;
import com.newsinfo.repository.NewsInitializerRepository;
import com.newsinfo.service.NewsFeederHelper;
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

@ExtendWith(MockitoExtension.class)
class NewsDetailsServiceImplTest {

    private NewsRequest newsRequest;
    private NewsInitializer newsInitializer;
    private EndorsersFeed endorsersFeed;
    private TransactionDetails transactionDetails;

    @InjectMocks
    private NewsDetailsServiceImpl newsDetailsServiceImpl;

    @Mock
    private NewsInitializerRepository newsInitializerRepository;

    @InjectMocks
    private NewsFeederHelper newsFeederHelper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        newsRequest = new NewsRequest();
        newsRequest.setTopic("Bruce Wayne is Batman");
        newsRequest.setEventLocation("Gotham");
        newsRequest.setImages("None");

        endorsersFeed = new EndorsersFeed();
        newsInitializer = new NewsInitializer();
        newsInitializer.setNewsId(1L);
        newsInitializer.setTopic("Bruce Wayne is Batman");
        newsInitializer.setLocation("Gotham");
        newsInitializer.setImages("None");
        newsInitializer.setUpdated(false);
        newsInitializer.setTransactionId("937c3c5f-7d9e-4d25-bd71-c40447d3eec7");
    }

    @Test
    void saveNews() {
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
}