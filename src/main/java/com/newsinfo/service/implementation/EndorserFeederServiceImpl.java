package com.newsinfo.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsinfo.aspect.logging.Logged;
import com.newsinfo.constants.Constants;
import com.newsinfo.dto.NewsEndorserFeedDAO;
import com.newsinfo.entity.EndorsersFeed;
import com.newsinfo.exceptions.PollException;
import com.newsinfo.repository.EndorsersFeedRepository;
import com.newsinfo.repository.NewsInitializerRepository;
import com.newsinfo.repository.PolledEndorsedNewsRepository;
import com.newsinfo.service.EndorserProfileService;
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
    private final EndorsersFeedRepository endorsersFeedRepository;
    private final PolledEndorsedNewsRepository polledEndorsedNewsRepository;
    private final EndorserProfileService endorserProfileService;

    @Override
    @Logged
    public EndorsersFeed createEndorserFeedEntry(long newsId, String transactionId) {
        EndorsersFeed endorsersFeed = new EndorsersFeed();
        endorsersFeed.setNewsId(newsId);
        endorsersFeed.setTransactionId(transactionId);
        endorsersFeed.setPollCount(Constants.INITIALIZER_POLL_COUNT);
        return endorsersFeed;
    }

    @Override
    @Logged
    public String getEndorsersFeed() {
        List<NewsEndorserFeedDAO> feeds = newsInitializerRepository.findAllSortedByNewsPosted();
        String stringFeeds = null;
        try {
            stringFeeds = new ObjectMapper().writeValueAsString(feeds);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return stringFeeds;
    }

    @Override
    public void voteForNews(String endorserId, String newsId) {
        validatePoll(endorserId, newsId);
        endorserProfileService.updateProfileForNewsPolled(endorserId, newsId);
        identifyAndPoll(newsId);
    }

    @Logged
    protected void identifyAndPoll(String newsId) {
        EndorsersFeed endorserPolled = endorsersFeedRepository
                .findById(Long.valueOf(newsId)).orElseThrow(() -> new EntityNotFoundException(newsId));
        AtomicInteger pollIncrement = new AtomicInteger(endorserPolled.getPollCount());
        endorserPolled.setPollCount(pollIncrement.incrementAndGet());

        endorsersFeedRepository.saveAndFlush(endorserPolled);
    }

    @Logged
    public void validatePoll(String endorserId, String newsId) {
        newsInitializerRepository.findById(Long.valueOf(newsId))
                .orElseThrow(() -> new PollException("Cannot cast for non-existent News"));

        polledEndorsedNewsRepository.findProfilePolledNews(endorserId, newsId)
                .ifPresent(alreadyPolled -> {
                    throw new PollException("Cannot re-cast vote on the News");
                });
    }
}
