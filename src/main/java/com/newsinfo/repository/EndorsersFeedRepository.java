package com.newsinfo.repository;

import com.newsinfo.entity.EndorsersFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndorsersFeedRepository extends JpaRepository<EndorsersFeed, Long> {

}
