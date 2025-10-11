package com.tech.user_insights.service;

import com.tech.user_insights.pojo.CountryDetails;
import com.tech.user_insights.pojo.DistrictDetails;
import com.tech.user_insights.pojo.StateDetails;

public interface UserMenuService {

	public CountryDetails getAllCountry();

	public StateDetails getAllStates(Integer countryCode);

	public DistrictDetails getAllDistrict(Integer stateCode);

}
