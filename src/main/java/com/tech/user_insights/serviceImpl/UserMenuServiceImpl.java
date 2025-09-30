package com.tech.user_insights.serviceImpl;

import org.springframework.stereotype.Service;

import com.tech.user_insights.pojo.CountryDetails;
import com.tech.user_insights.pojo.DistrictDetails;
import com.tech.user_insights.pojo.StateDetails;
import com.tech.user_insights.service.UserMenuService;

@Service
public class UserMenuServiceImpl implements UserMenuService{

	@Override
	public CountryDetails getAllCountry() {
		
		return null;
	}

	@Override
	public StateDetails getAllStates(Integer countryCode) {
		return null;
	}

	@Override
	public DistrictDetails getAllDistrict(Integer stateCode) {
		return null;
	}
	
	

}
