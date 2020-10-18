package com.newsinfo.controller;

import com.newsinfo.aspect.logging.Logged;
import com.newsinfo.model.endorser.RegisterRequest;
import com.newsinfo.repository.EndorserProfileRepository;
import com.newsinfo.service.EndorserProfileService;
import com.newsinfo.service.EndorsersFeederService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/endorser", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EndorsersController {

    private final EndorserProfileRepository endorserProfileRepository;
    private final EndorserProfileService endorserProfileService;
    private final EndorsersFeederService endorsersFeederService;

    @PostMapping("/register")
    @Logged
    public String registerEndorser(@RequestBody RegisterRequest registerRequest) {
        return endorserProfileService.registerEndorser(registerRequest);
    }

    /**
     * Get Method for fetching the Endorser News Feeds information with NewsTopic that were polled Generic to display
     * NewsTopic and the current number of endorsers
     */
    @GetMapping("/allEndorsersFeed")
    @Logged
    public ResponseEntity<String> fetchEndorsersFeedData() {
        String feedData = endorsersFeederService.getEndorsersFeed();
        return ResponseEntity.ok(feedData);
    }

    /**
     * Put method for Endorser to poll on the NewsTopic and redirect call to Endorser profile, for recording the
     * NewsTopic and their poll and internally call to persist Endorsers List based on time to get initial 100 endorsers
     * for rewarding
     */
    @PutMapping("/{endorserId}/pollForNews/{newsId}")
    @Logged
    public ResponseEntity<?> castVoteForNews(@PathVariable String endorserId, @PathVariable String newsId) {
        endorsersFeederService.voteForNews(endorserId, newsId);
        return ResponseEntity.of(Optional.empty());
    }
}
