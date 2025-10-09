package com.tech.user_insights.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.user_insights.pojo.SpotDetails;

@Repository
public interface SpotDetailsRepo extends JpaRepository<SpotDetails, Long>{

	SpotDetails findBySpotName(String spotName);

}
