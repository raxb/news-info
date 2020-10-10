package com.newsinfo.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsinfo.constants.Constants;
import com.newsinfo.dto.NewsInitializerDAO;
import com.newsinfo.entity.EndorserProfile;
import com.newsinfo.entity.EndorsersFeed;
import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.model.endorser.RegisterRequest;
import com.newsinfo.repository.EndorserProfileRepository;
import com.newsinfo.repository.EndorsersFeedRepository;
import com.newsinfo.repository.NewsInitializerRepository;
import com.newsinfo.service.EndorsersFeederService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EndorserFeederServiceImpl implements EndorsersFeederService {

    private final NewsInitializerRepository newsInitializerRepository;
    private final EndorserProfileRepository endorserProfileRepository;
    private final EndorsersFeedRepository endorsersFeedRepository;

    public void createEndorserEntry(NewsInitializer newsInitializer, NewsInitializerDAO newsInitializerDAO) {
        EndorsersFeed endorsersFeed = new EndorsersFeed();
        endorsersFeed.setNewsId(newsInitializer.getNewsId());
        endorsersFeed.setTransactionId(newsInitializerDAO.getTransactionId());
        endorsersFeed.setPollCount(Constants.INITIALIZER_POLL_COUNT);
        newsInitializer.setEndorsersFeed(endorsersFeed);
    }

    @Override
    public String registerEndorser(RegisterRequest registerRequest) {
        EndorserProfile newProfile = new EndorserProfile();
        newProfile.setFirstName(registerRequest.getFirstName());
        newProfile.setLastName(registerRequest.getLastName());
        newProfile.setLocation(registerRequest.getLocation());
        newProfile.setPoints(Constants.NEW_PROFILE_ENTRY_POINTS);
        endorserProfileRepository.save(newProfile);
        return newProfile.getId();
    }

    @Override
    public String getEndorsersFeed() {
        List<NewsInitializer> feeds = newsInitializerRepository.findAllSortedByNewsPosted();
        String stringFeeds = null;
        try {
            stringFeeds = new ObjectMapper().writeValueAsString(feeds);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return stringFeeds;
    }

    @Override
    public void voteForNews(String newsId) {
        EndorsersFeed endorserPolled = endorsersFeedRepository.
                findById(Long.valueOf(newsId)).orElseThrow(() -> new EntityNotFoundException(newsId));
        AtomicInteger pollIncrement = new AtomicInteger(endorserPolled.getPollCount());
        endorserPolled.setPollCount(pollIncrement.incrementAndGet());
        endorsersFeedRepository.saveAndFlush(endorserPolled);
    }
}
