package com.newsinfo.repository;

import com.newsinfo.entity.NewsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for CRUD operations
 */
@Repository
public interface NewsDetailsRepository extends JpaRepository<NewsDetails, Long> {
}
