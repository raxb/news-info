package com.newsinfo.repository;

import com.newsinfo.entity.ReporterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ReporterProfileRepository extends JpaRepository<ReporterProfile, String> {

}