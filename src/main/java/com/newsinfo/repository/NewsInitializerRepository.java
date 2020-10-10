package com.newsinfo.repository;

import com.newsinfo.entity.NewsInitializer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * JPA Repository for CRUD operations
 */
@Repository
@Transactional
public interface NewsInitializerRepository extends JpaRepository<NewsInitializer, Long> {

    @Query("SELECT ni FROM NewsInitializer ni WHERE ni.transactionId = :transactionId")
    NewsInitializer findByTransactionId(@Param("transactionId") String transactionId);

    @Query(value = "select ni.* from news_info.news_initializer ni, news_info.endorsers_feed ef where " +
            "ni.news_Id = ef.news_Id and " +
            "((str_to_date(ni.transaction_time,'%H:%i:%s') < (select subtime(now(),'00:15:00')) and date" +
            "(ni.transaction_date) = date(now())) or date(ni.transaction_date) < date(now())) order by " +
            "ni.transaction_date desc, ni.transaction_time desc", nativeQuery = true)
    List<NewsInitializer> findAllSortedByNewsPosted();

}
