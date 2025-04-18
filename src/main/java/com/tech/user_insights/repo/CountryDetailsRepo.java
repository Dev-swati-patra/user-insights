package com.tech.user_insights.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.user_insights.pojo.CountryDetails;

@Repository
public interface CountryDetailsRepo extends JpaRepository<CountryDetails, Long> {
	
	CountryDetails findByCountryName(String countryName);
}
