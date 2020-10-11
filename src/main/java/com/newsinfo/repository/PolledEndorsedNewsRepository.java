package com.newsinfo.repository;

import com.newsinfo.entity.PolledEndorsedNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface PolledEndorsedNewsRepository extends JpaRepository<PolledEndorsedNews, Long> {

    @Query("select pen from PolledEndorsedNews pen, EndorserProfile ep where ep.id = pen" +
            ".endorserProfile.id and ep.id = :endorserId and pen.newsId = :newsId")
    Optional<PolledEndorsedNews> findProfilePolledNews(@Param("endorserId") String endorserId, @Param(
            "newsId") String newsId);
}
