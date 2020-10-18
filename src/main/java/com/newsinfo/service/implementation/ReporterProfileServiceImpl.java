package com.newsinfo.service.implementation;

import com.newsinfo.aspect.logging.Logged;
import com.newsinfo.constants.Constants;
import com.newsinfo.entity.NewsInitializer;
import com.newsinfo.entity.ReporterProfile;
import com.newsinfo.model.endorser.RegisterRequest;
import com.newsinfo.repository.ReporterProfileRepository;
import com.newsinfo.service.ReporterProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ReporterProfileServiceImpl implements ReporterProfileService {

    private final ReporterProfileRepository reporterProfileRepository;

    @Override
    @Logged
    public String registerReporter(RegisterRequest registerRequest) {
        ReporterProfile newProfile = new ReporterProfile();
        newProfile.setFirstName(registerRequest.getFirstName());
        newProfile.setLastName(registerRequest.getLastName());
        newProfile.setLocation(registerRequest.getLocation());
        newProfile.setPoints(Constants.NEW_PROFILE_ENTRY_POINTS);
        reporterProfileRepository.save(newProfile);
        return newProfile.getId();
    }

    @Override
    @Logged
    public void addReportedNews(String reporterId, NewsInitializer newsInitializer) {
        ReporterProfile newsReporterBy =
                reporterProfileRepository.findById(reporterId).orElseThrow(() -> new EntityNotFoundException(reporterId));
        newsReporterBy.addNewsInitializer(newsInitializer);
        reporterProfileRepository.save(newsReporterBy);
    }

    @Override
    @Logged
    public void deleteReportedNews(String reporterId, NewsInitializer newsForDeletion) {
        ReporterProfile newsReporterBy =
                reporterProfileRepository.findById(reporterId).orElseThrow(() -> new EntityNotFoundException(reporterId));
        newsReporterBy.removeNewsInitializer(newsForDeletion);
        reporterProfileRepository.flush();
    }
}
