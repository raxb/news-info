package com.newsinfo.repository;

import com.newsinfo.entity.EndorserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EndorserProfileRepository extends JpaRepository<EndorserProfile, String> {

}