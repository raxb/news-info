package com.newsinfo.service.implementation;

import com.newsinfo.aspect.logging.Logged;
import com.newsinfo.constants.Constants;
import com.newsinfo.entity.EndorserProfile;
import com.newsinfo.entity.PolledEndorsedNews;
import com.newsinfo.model.endorser.RegisterRequest;
import com.newsinfo.repository.EndorserProfileRepository;
import com.newsinfo.service.EndorserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EndorserProfileServiceImpl implements EndorserProfileService {

    private final EndorserProfileRepository endorserProfileRepository;

    @Override
    @Logged
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
    @Logged
    public void updateProfileForNewsPolled(String endorserId, String newsId) {
        EndorserProfile endorserProfile = endorserProfileRepository.findById(endorserId).orElseThrow(() -> new EntityNotFoundException(newsId));
        endorserProfile.addPolledEndorsedNews(new PolledEndorsedNews(newsId));
        endorserProfileRepository.saveAndFlush(endorserProfile);
    }
}
