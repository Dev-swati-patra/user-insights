package com.tech.user_insights.service;

import com.tech.user_insights.pojo.UserInfo;
import com.tech.user_insights.pojo.UserLoginInfo;

public interface MasterService {

	UserInfo getDataByUserName(String userName);

	boolean isStateNamePresent(String stateName);

	boolean isDistrictNamePresent(String districtName);

	boolean isCountryNamePresent(String countryName);

	Integer getStateCode(String stateName);

	Integer getDistrictCode(String districtName);

	Integer getCountryCode(String countryName);

	String getStateShortName(String stateName);

	String getDistrictShortName(String districtName);

	String getCountryShortName(String countryName);

	boolean isValidCountryName(String countryName);

	boolean isValidDistrictName(String districtName, String stateName);

	boolean isValidStateName(String stateName, String countryName);

	void saveUserInfoDetails(UserInfo userInfo);

	void saveUserLoginInfoDetails(UserLoginInfo loginInfo);

}
