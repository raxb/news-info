package com.newsinfo.service.implementation;

import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.initializer.InitializerApplication;
import com.newsinfo.model.endorser.RegisterRequest;
import com.newsinfo.repository.NewsInitializerRepository;
import com.newsinfo.repository.ReporterProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = InitializerApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ReporterProfileServiceImplTest {

    @Autowired
    private ReporterProfileRepository reporterProfileRepository;

    @Autowired
    private NewsInitializerRepository newsInitializerRepository;

    @Autowired
    private ReporterProfileServiceImpl reporterProfileService;

    private String testReporterId;

    @BeforeEach
    public void testReporter() {
        RegisterRequest testRegister = new RegisterRequest();
        testRegister.setFirstName("FirstX");
        testRegister.setLastName("LastX");
        testRegister.setLocation("CoimbatoreX");
        testReporterId = reporterProfileService.registerReporter(testRegister);
    }

    @Test
    public void testRegisterReporter() {
        assertNotNull(testReporterId);
        assertNotNull(reporterProfileRepository.findById(testReporterId));
    }

    @Test
    public void testAddReportedNews() {
        NewsInitializer testNews = new NewsInitializer();
        testNews.setTopic("Test_News_Topic");
        testNews.setLocation("Test_Location");
        testNews.setImages("Test_Images");

        reporterProfileService.addReportedNews(testReporterId, testNews);
        assertTrue(reporterProfileRepository.findById(testReporterId).isPresent());
        assertNotNull(reporterProfileRepository.findById(testReporterId).get().getNewsInitializerSet());
        assertTrue(reporterProfileRepository.findById(testReporterId).get().getNewsInitializerSet().size() > 0);

        Set<NewsInitializer> newsInitializers =
                reporterProfileRepository.findById(testReporterId).get().getNewsInitializerSet();
        Long newsId = newsInitializers.iterator().next().getNewsId();
        Optional<NewsInitializer> expectedNews = newsInitializerRepository.findById(newsId);

        assertTrue(expectedNews.isPresent());
        assertTrue(reporterProfileRepository.findById(testReporterId).get().getNewsInitializerSet().contains(expectedNews.get()));
    }

    @Test
    public void testAddReportedNewsReporterIdNotExists() {
        NewsInitializer testNews = new NewsInitializer();
        testNews.setTopic("Test_News_Topic");
        testNews.setLocation("Test_Location");
        testNews.setImages("Test_Images");

        assertThrows(EntityNotFoundException.class, () -> reporterProfileService.addReportedNews("REP_12345",
                testNews));
    }

    @Test
    public void testDeleteReportedNews() {
        NewsInitializer testNews = new NewsInitializer();
        testNews.setTopic("Test_News_Topic");
        testNews.setLocation("Test_Location");
        testNews.setImages("Test_Images");

        reporterProfileService.deleteReportedNews(testReporterId, testNews);
        assertTrue(reporterProfileRepository.findById(testReporterId).isPresent());
        assertEquals(reporterProfileRepository.findById(testReporterId).get().getNewsInitializerSet().size(), 0);
    }

    @Test
    public void testDeleteReportedNewsReporterIdNotExists() {
        NewsInitializer testNews = new NewsInitializer();
        testNews.setTopic("Test_News_Topic");
        testNews.setLocation("Test_Location");
        testNews.setImages("Test_Images");

        assertThrows(EntityNotFoundException.class, () -> reporterProfileService.deleteReportedNews("REP_12345",
                testNews));
    }
}