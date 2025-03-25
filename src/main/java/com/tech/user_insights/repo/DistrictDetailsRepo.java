package com.tech.user_insights.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.user_insights.pojo.DistrictDetails;

@Repository
public interface DistrictDetailsRepo extends JpaRepository<DistrictDetails, Long> {

}
