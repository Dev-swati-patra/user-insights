package com.tech.user_insights.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.user_insights.pojo.CountryDetails;
import com.tech.user_insights.pojo.DistrictDetails;
import com.tech.user_insights.pojo.StateDetails;
import com.tech.user_insights.pojo.UserInfo;
import com.tech.user_insights.repo.CountryDetailsRepo;
import com.tech.user_insights.repo.DistrictDetailsRepo;
import com.tech.user_insights.repo.StateDetailsrepo;
import com.tech.user_insights.repo.UserInfoRepo;
import com.tech.user_insights.service.MasterService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MasterServiceImpl implements MasterService {

	@Autowired
	private UserInfoRepo infoRepo;

	@Autowired
	private DistrictDetailsRepo districtDetailsRepo;

	@Autowired
	private StateDetailsrepo stateDetailsrepo;

	@Autowired
	private CountryDetailsRepo countryDetailsRepo;

	@Override
	public boolean isStateNamePresent(String stateName) {
		StateDetails stateDetails = stateDetailsrepo.findByStateName(stateName);
		return null != stateDetails;
	}

	@Override
	public boolean isDistrictNamePresent(String districtName) {
		DistrictDetails districtDetails = districtDetailsRepo.findByDistrictName(districtName);
		return null != districtDetails;
	}

	@Override
	public boolean isCountryNamePresent(String countryName) {
		CountryDetails countryDetails = countryDetailsRepo.findByCountryName(countryName);
		return null != countryDetails;
	}

	@Override
	public Integer getStateCode(String stateName) {
		StateDetails stateDetails = stateDetailsrepo.findByStateName(stateName);
		return stateDetails.getStateCode();
	}

	@Override
	public Integer getDistrictCode(String districtName) {
		DistrictDetails districtDetails = districtDetailsRepo.findByDistrictName(districtName);
		return districtDetails.getDistrictCode();
	}

	@Override
	public Integer getCountryCode(String countryName) {
		CountryDetails countryDetails = countryDetailsRepo.findByCountryName(countryName);
		return countryDetails.getCountryCode();
	}

	@Override
	public String getStateShortName(String stateName) {
		StateDetails stateDetails = stateDetailsrepo.findByStateName(stateName);
		return stateDetails.getStateShortName();
	}

	@Override
	public String getDistrictShortName(String districtName) {
		DistrictDetails districtDetails = districtDetailsRepo.findByDistrictName(districtName);
		return districtDetails.getDistrictShortName();
	}

	@Override
	public String getCountryShortName(String countryName) {
		CountryDetails countryDetails = countryDetailsRepo.findByCountryName(countryName);
		return countryDetails.getCountryShortName();
	}

	@Override
	public boolean isValidDistrictName(String districtName, String stateName) {
		if (isDistrictNamePresent(districtName)) {
			return districtDetailsRepo.findByDistrictName(districtName).getStateCode() == getStateCode(stateName);
		}
		return false;
	}

	@Override
	public boolean isValidStateName(String stateName, String countryName) {

		if (isStateNamePresent(stateName)) {
			return stateDetailsrepo.findByStateName(stateName).getCountryCode() == getCountryCode(countryName);
		}
		return false;
	}

	@Override
	public boolean isValidCountryName(String countryName) {
		CountryDetails countryDetails = countryDetailsRepo.findByCountryName(countryName);
		return null != countryDetails.getCountryName();
	}

	@Override
	public UserInfo getDataByUserName(String userName) {
		return infoRepo.findByUserName(userName);
	}

	@Override
	public void saveUserInfoDetails(UserInfo userInfo) {
		infoRepo.save(userInfo);
	}
	
}
