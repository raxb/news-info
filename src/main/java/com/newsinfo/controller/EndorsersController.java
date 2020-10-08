package com.newsinfo.controller;

import com.newsinfo.constants.Constants;
import com.newsinfo.entity.EndorserProfile;
import com.newsinfo.model.endorser.RegisterRequest;
import com.newsinfo.repository.EndorserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/endorser", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EndorsersController {

    private final EndorserProfileRepository endorserProfileRepository;

    @PostMapping("/register")
    public String registerEndorser(@RequestBody RegisterRequest registerRequest) {
        EndorserProfile newProfile = new EndorserProfile();
        newProfile.setFirstName(registerRequest.getFirstName());
        newProfile.setLastName(registerRequest.getLastName());
        newProfile.setLocation(registerRequest.getLocation());
        newProfile.setPoints(Constants.NEW_PROFILE_ENTRY_POINTS);
        endorserProfileRepository.save(newProfile);
        return newProfile.getId();
    }
}
