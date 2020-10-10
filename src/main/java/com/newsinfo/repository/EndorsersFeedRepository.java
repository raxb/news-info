package com.newsinfo.repository;

import com.newsinfo.entity.EndorsersFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EndorsersFeedRepository extends JpaRepository<EndorsersFeed, Long> {

}
