package com.tech.user_insights.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.user_insights.pojo.StateDetails;

@Repository
public interface StateDetailsrepo extends JpaRepository<StateDetails, Long> {
	
	Integer findByStateName(String stateName);

}
