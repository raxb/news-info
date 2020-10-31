package com.newsinfo.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newsinfo.entity.EndorserProfile;
import com.newsinfo.entity.EndorsersFeed;
import com.newsinfo.entity.PolledEndorsedNews;
import com.newsinfo.exceptions.PollException;
import com.newsinfo.initializer.InitializerApplication;
import com.newsinfo.repository.EndorsersFeedRepository;
import com.newsinfo.repository.NewsInitializerRepository;
import com.newsinfo.repository.PolledEndorsedNewsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = InitializerApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class EndorserFeederServiceImplTest {

    @InjectMocks
    private EndorserFeederServiceImpl endorserFeederService;

    @Mock
    private EndorsersFeedRepository endorsersFeedRepository;

    @Mock
    private PolledEndorsedNewsRepository polledEndorsedNewsRepository;

    @Mock
    private NewsInitializerRepository newsInitializerRepository;

    @Test
    public void testCreateEndorserFeedEntry() {
        long testNewsId = 1L;
        EndorsersFeed expectedEndorsersFeed = endorserFeederService.createEndorserFeedEntry(testNewsId,
                "df030461-8b91-4ceb-83c3-ca7c4a4db19d");

        doReturn(Optional.of(expectedEndorsersFeed)).when(endorsersFeedRepository).findById(testNewsId);
        assertEquals(0, expectedEndorsersFeed.getPollCount());
    }

    @Test
    public void testGetEndorsersFeed() {
        assertNotNull(endorserFeederService.getEndorsersFeed());
    }

    @Test
    public void testGetEndorsersFeedError() {
        given(endorserFeederService.getEndorsersFeed()).willAnswer(invocation -> {
            throw new JsonProcessingException("Error") {
            };
        });
        assertThrows(JsonProcessingException.class, () -> endorserFeederService.getEndorsersFeed());
    }

    @Test
    public void testValidateAlreadyPoll() {
        EndorserProfile testProfile = new EndorserProfile();
        testProfile.setFirstName("Endorser_FirstX");
        testProfile.setLastName("Endorser_LastX");
        testProfile.setLocation("LocationX");
        testProfile.setPoints(0);
        testProfile.setId("NEN_12345");

        PolledEndorsedNews testPolledEndorsedNews = new PolledEndorsedNews();
        testPolledEndorsedNews.setId(1L);
        testPolledEndorsedNews.setNewsId("1");
        testPolledEndorsedNews.setPolledDateTimestamp("2020-10-24T07:36:27.502");

        testProfile.addPolledEndorsedNews(testPolledEndorsedNews);
        doReturn(Optional.of(testPolledEndorsedNews)).when(polledEndorsedNewsRepository).findProfilePolledNews(testProfile.getId(),
                testPolledEndorsedNews.getNewsId());
        assertThrows(PollException.class,
                () -> endorserFeederService.validatePoll(testProfile.getId(),
                        testPolledEndorsedNews.getNewsId()));
    }

    @Test
    public void testValidatePoll() {
        EndorserProfile testProfile = new EndorserProfile();
        testProfile.setFirstName("Endorser_FirstX");
        testProfile.setLastName("Endorser_LastX");
        testProfile.setLocation("LocationX");
        testProfile.setPoints(0);
        testProfile.setId("NEN_12345");

        PolledEndorsedNews testPolledEndorsedNews = new PolledEndorsedNews();
        testPolledEndorsedNews.setId(1L);
        testPolledEndorsedNews.setNewsId("1");
        testPolledEndorsedNews.setPolledDateTimestamp("2020-10-24T07:36:27.502");

        doReturn(Optional.empty()).when(polledEndorsedNewsRepository).findProfilePolledNews(testProfile.getId(),
                testPolledEndorsedNews.getNewsId());
        assertDoesNotThrow(() -> endorserFeederService.validatePoll(testProfile.getId(),
                testPolledEndorsedNews.getNewsId()));
    }

    @Test
    public void testIdentifyAndPoll() {
        String testNewsId = "1";
        EndorsersFeed testFeed = new EndorsersFeed();
        testFeed.setNewsId(Long.valueOf(testNewsId));
        testFeed.setTransactionId("df030461-8b91-4ceb-83c3-ca7c4a4db19d");
        testFeed.setPollCount(0);

        doReturn(Optional.of(testFeed)).when(endorsersFeedRepository).findById(Long.valueOf(testNewsId));
        endorserFeederService.identifyAndPoll(testNewsId);
        assertEquals(1, testFeed.getPollCount());
    }

    @Test
    public void testIdentifyAndPollNewsIdNA() {
        String testNewsId = "1";
        doReturn(Optional.empty()).when(endorsersFeedRepository).findById(Long.valueOf(testNewsId));
        assertThrows(EntityNotFoundException.class, () -> endorserFeederService.identifyAndPoll(testNewsId));
    }

}