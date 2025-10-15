package com.tech.user_insights.service;

import java.util.List;

import com.tech.user_insights.constants.StatusMessage;
import com.tech.user_insights.dto.UserLoginInfoDto;
import com.tech.user_insights.pojo.BookingManagement;
import com.tech.user_insights.pojo.OtpVerification;
import com.tech.user_insights.pojo.SpotDetails;
import com.tech.user_insights.pojo.UserAgencyInfo;
import com.tech.user_insights.pojo.UserInfo;
import com.tech.user_insights.pojo.UserLoginInfo;
import com.tech.user_insights.responsedto.ResponseDto;

public interface MasterService {

	String getUserName();

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

	UserInfo getDataByUserEmail(String userEmail);

	void saveOtpVerificationDetails(OtpVerification otpVerification);

//	OtpVerification getOtpVerificationData(String userName);

	void saveSpotDetails(SpotDetails spotDetails);

	List<SpotDetails> fetchAllSpot(StatusMessage active);

	SpotDetails getDataBySpotName(String spotName);

	void saveBookingManagementDetails(BookingManagement bookingManagement);

	List<BookingManagement> getBookManagementDataByUserId(Integer userId);

	BookingManagement getBookingDetailsById(Long bookingId);

	void saveUserAgencyInfoDetails(UserAgencyInfo userAgencyInfo);

	List<UserAgencyInfo> getUserAgencyInfoDetails(String userName);

	UserInfo getDataByUserPhoneNumber(String userPhoneNumber);

	List<UserAgencyInfo> getuserFilterdData(String userName, String userEmail, long long1, String approvalStatus);

	List<UserAgencyInfo> getUserDataByUserEmail(String userEmail);

	List<UserAgencyInfo> getUserDataByUserPhoneNumber(Long userPhoneNumber);

	List<UserAgencyInfo> getUserDataByUserName(String userName);

	ResponseDto sendOtpToUser(String userName);

	Boolean verifyOtp(UserLoginInfoDto loginInfoDto);

	String generateRandomPassword();

}
