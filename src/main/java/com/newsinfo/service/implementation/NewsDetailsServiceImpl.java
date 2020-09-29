package com.newsinfo.service.implementation;

import com.newsinfo.dto.NewsDetailsDTO;
import com.newsinfo.entity.NewsDetails;
import com.newsinfo.repository.NewsDetailsRepository;
import com.newsinfo.service.NewsDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class handling low-level implementations and processings
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NewsDetailsServiceImpl implements NewsDetailsService {

    private final NewsDetailsRepository newsDetailsRepository;

    /**
     * Persist to the defined Entity
     *
     * @param newsDetailsDTO object for data-store
     * @return transactionId for reference
     */
    @Override
    public String saveNews(NewsDetailsDTO newsDetailsDTO) {
        NewsDetails newsDetails = populateNewsDetailsEntity(newsDetailsDTO);
        return newsDetailsRepository.save(newsDetails).getTransactionId();
    }

    /**
     * Mapper from DTO to Entity
     *
     * @param newsDetailsDTO object for data-store
     * @return persistent Entity
     */
    private NewsDetails populateNewsDetailsEntity(NewsDetailsDTO newsDetailsDTO) {
        NewsDetails newsDetails = new NewsDetails();
        newsDetails.setTransactionId(newsDetailsDTO.getTransactionId());
        newsDetails.setTopic(newsDetailsDTO.getTopic());
        newsDetails.setLocation(newsDetailsDTO.getLocation());
        newsDetails.setReporterId(newsDetailsDTO.getReporterId());
        newsDetails.setImages(newsDetailsDTO.getImages());
        newsDetails.setTransactionTimestamp(newsDetailsDTO.getTransactionTimestamp());
        return newsDetails;
    }
}
