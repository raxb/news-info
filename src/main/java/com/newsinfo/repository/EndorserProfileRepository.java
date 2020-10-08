package com.newsinfo.repository;

import com.newsinfo.entity.EndorserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndorserProfileRepository extends JpaRepository<EndorserProfile, String> {

}