package com.newsinfo.repository;

import com.newsinfo.dto.NewsEndorserFeedDAO;
import com.newsinfo.entity.NewsInitializer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * JPA Repository for CRUD operations
 */
@Repository
@Transactional
public interface NewsInitializerRepository extends JpaRepository<NewsInitializer, Long> {

    @Query(name = "NewsInitializer.NewsEndorserFeedDataQuery", nativeQuery = true)
    List<NewsEndorserFeedDAO> findAllSortedByNewsPosted();
}
