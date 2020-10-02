package com.newsinfo.repository;

import com.newsinfo.entity.NewsInitializer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for CRUD operations
 */
@Repository
public interface NewsInitializerRepository extends JpaRepository<NewsInitializer, Long> {

    @Query("SELECT ni FROM NewsInitializer ni WHERE ni.transactionId = :transactionId")
    NewsInitializer findByTransactionId(@Param("transactionId") String transactionId);
}
