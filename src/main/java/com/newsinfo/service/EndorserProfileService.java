package com.newsinfo.service;

import com.newsinfo.model.endorser.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface EndorserProfileService {

    String registerEndorser(RegisterRequest registerRequest);

    void updateProfileForNewsPolled(String endorserId, String newsId);
}
