package com.newsinfo.repository;

import com.newsinfo.entity.NewsInitializer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for CRUD operations
 */
@Repository
public interface NewsInitializerRepository extends JpaRepository<NewsInitializer, Long> {

    @Query("SELECT ni FROM NewsInitializer ni WHERE ni.transactionId = :transactionId")
    NewsInitializer findByTransactionId(@Param("transactionId") String transactionId);

    @Query(value = "select * from news_info.news_initializer where \n" +
            "(str_to_date(transaction_time,'%H:%i:%s') < (select subtime(now(),'00:15:00')) and date" +
            "(transaction_date) = date(now())) or date(transaction_date) < date(now()) order by transaction_date " +
            "desc, transaction_time desc", nativeQuery = true)
    List<NewsInitializer> findAllSortedByNewsPosted();
}
