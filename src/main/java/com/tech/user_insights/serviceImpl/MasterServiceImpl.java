package com.tech.user_insights.serviceImpl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tech.user_insights.constants.StatusMessage;
import com.tech.user_insights.constants.StringUtils;
import com.tech.user_insights.dto.UserLoginInfoDto;
import com.tech.user_insights.pojo.BookingManagement;
import com.tech.user_insights.pojo.CountryDetails;
import com.tech.user_insights.pojo.DistrictDetails;
import com.tech.user_insights.pojo.OtpVerification;
import com.tech.user_insights.pojo.SpotDetails;
import com.tech.user_insights.pojo.StateDetails;
import com.tech.user_insights.pojo.UserAgencyInfo;
import com.tech.user_insights.pojo.UserInfo;
import com.tech.user_insights.pojo.UserLoginInfo;
import com.tech.user_insights.repo.BookingManagementRepo;
import com.tech.user_insights.repo.CountryDetailsRepo;
import com.tech.user_insights.repo.DistrictDetailsRepo;
import com.tech.user_insights.repo.OtpVerificationRepo;
import com.tech.user_insights.repo.SpotDetailsRepo;
import com.tech.user_insights.repo.StateDetailsrepo;
import com.tech.user_insights.repo.UserAgencyInfoRepo;
import com.tech.user_insights.repo.UserInfoRepo;
import com.tech.user_insights.repo.UserLoginInfoRepo;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.MasterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MasterServiceImpl implements MasterService {

	private final UserInfoRepo infoRepo;
	private final UserLoginInfoRepo loginInfoRepo;
	private final DistrictDetailsRepo districtDetailsRepo;
	private final StateDetailsrepo stateDetailsrepo;
	private final CountryDetailsRepo countryDetailsRepo;
	private final OtpVerificationRepo otpVerificationRepo;
	private final SpotDetailsRepo spotDetailsRepo;
	private final BookingManagementRepo bookingManagementRepo;
	private final UserAgencyInfoRepo userAgencyInfoRepo;

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
			return districtDetailsRepo.findByDistrictName(districtName).getState()
					.getStateCode() == getStateCode(stateName);
		}
		return false;
	}

	@Override
	public boolean isValidStateName(String stateName, String countryName) {

		if (isStateNamePresent(stateName)) {
			return stateDetailsrepo.findByStateName(stateName).getCountry()
					.getCountryCode() == getCountryCode(countryName);
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

	@Override
	public void saveUserLoginInfoDetails(UserLoginInfo loginInfo) {
		loginInfoRepo.save(loginInfo);
	}

	@Override
	public UserInfo getDataByUserEmail(String userEmail) {
		return infoRepo.findByUserEmail(userEmail);
	}

	@Override
	public void saveOtpVerificationDetails(OtpVerification otpVerification) {
		otpVerificationRepo.save(otpVerification);
	}

//	@Override
//	public OtpVerification getOtpVerificationData(String userName) {
//		OtpVerification otpVerification = null;
//		if (!StringUtils.isEmpty(userName)) {
//			otpVerification = otpVerificationRepo.findByUserName(userName);
//		}
//		return otpVerification;
//	}

	@Override
	public void saveSpotDetails(SpotDetails spotDetails) {
		spotDetailsRepo.save(spotDetails);
	}

	@Override
	public List<SpotDetails> fetchAllSpot(String active) {
		return spotDetailsRepo.findByStatus(active);
	}

	@Override
	public SpotDetails getDataBySpotName(String spotName) {
		return spotDetailsRepo.findBySpotName(spotName);
	}

	@Override
	public void saveBookingManagementDetails(BookingManagement bookingManagement) {
		bookingManagementRepo.save(bookingManagement);
	}

	@Override
	public List<BookingManagement> getBookManagementDataByUserName(String userName) {
		return bookingManagementRepo.findByUsername(userName);
	}

	@Override
	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			} else {
				return principal.toString();
			}
		}
		return null;
	}

	@Override
	public List<BookingManagement> getBookingDetailsByBookingRefId(String bookingRefId) {
		return bookingManagementRepo.findByBookingRefId(bookingRefId);
	}

	@Override
	public void saveUserAgencyInfoDetails(UserAgencyInfo userAgencyInfo) {
		userAgencyInfoRepo.save(userAgencyInfo);

	}

	@Override
	public List<UserAgencyInfo> getUserAgencyInfoDetails(String userName) {
		return userAgencyInfoRepo.findByUserNameAndApprovalStatus(userName, StatusMessage.UNVERIFIED);
	}

	@Override
	public UserInfo getDataByUserPhoneNumber(String userPhoneNumber) {
		return infoRepo.findByUserPhoneNumber(userPhoneNumber);
	}

	@Override
	public List<UserAgencyInfo> getuserFilterdData(String userName, String userEmail, long phonNumber,
			String approvalStatus) {
		return userAgencyInfoRepo.findByUserNameOrUserEmailOrUserPhoneNumberOrApprovalStatus(userName, userEmail,
				phonNumber, approvalStatus);
	}

	@Override
	public List<UserAgencyInfo> getUserDataByUserEmail(String userEmail) {
		return userAgencyInfoRepo.findByUserEmailAndApprovalStatusAndIsActive(userEmail, StatusMessage.UNVERIFIED,
				true);
	}

	@Override
	public List<UserAgencyInfo> getUserDataByUserPhoneNumber(Long userPhoneNumber) {
		return userAgencyInfoRepo.findByUserPhoneNumberAndApprovalStatusAndIsActive(userPhoneNumber,
				StatusMessage.UNVERIFIED, true);
	}

	@Override
	public List<UserAgencyInfo> getUserDataByUserName(String userName) {
		return userAgencyInfoRepo.findByUserNameAndApprovalStatusAndIsActive(userName, StatusMessage.UNVERIFIED, true);
	}

	@Override
	public ResponseDto sendOtpToUser(String userName) {
		OtpVerification otpVerification = null;
		ResponseDto response = new ResponseDto();
		if (StringUtils.isValidObj(userName)) {
			otpVerification = otpVerificationRepo.findByUserName(userName);
			if (StringUtils.isValidObj(otpVerification))
				otpVerification = new OtpVerification();
			String otp = StringUtils.generateSixDigitOtp();
			otpVerification.setOtp(otp);
			otpVerification.setVerified(false);
			otpVerification.setUserName(userName);
			otpVerification.setExpiryTime(LocalDateTime.now().plusMinutes(10));
			otpVerificationRepo.save(otpVerification);
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>> OTP:---" + otp);
			response.setStatus("SUCCESS");
			response.setMessage("OTP generated successfully");
			return response;
		}
		response.setStatus(StatusMessage.FAIL);
		response.setMessage("Something went wrong");
		return response;
	}

	@Override
	public Boolean verifyOtp(UserLoginInfoDto loginInfoDto) {
		OtpVerification otpVerification = null;
		if (StringUtils.isValidObj(loginInfoDto) && !StringUtils.isEmpty(loginInfoDto.getUserName())
				&& !StringUtils.isEmpty(loginInfoDto.getOtp())) {
			otpVerification = otpVerificationRepo.findByUserName(loginInfoDto.getUserName());
			if (StringUtils.isValidObj(otpVerification)) {
				if (otpVerification.getOtp().equals(loginInfoDto.getOtp()) && !otpVerification.isVerified()
						&& otpVerification.getExpiryTime().isAfter(LocalDateTime.now())) {
					otpVerification.setVerified(true);
					otpVerificationRepo.save(otpVerification);
					return true;
				}
			}
		}
		return null;
	}

	@Override
	public String generateRandomPassword() {
		String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCase = "abcdefghijklmnopqrstuvwxyz";
		String digits = "0123456789";
		String symbols = "!@#$%^&*";

		String allChars = upperCase + lowerCase + digits + symbols;
		StringBuilder password = new StringBuilder(8);
		SecureRandom random = new SecureRandom();

		// Ensure at least one character from each group (optional but recommended)
		password.append(upperCase.charAt(random.nextInt(upperCase.length())));
		password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
		password.append(digits.charAt(random.nextInt(digits.length())));
		password.append(symbols.charAt(random.nextInt(symbols.length())));

		// Fill remaining characters
		for (int i = 4; i < 8; i++) {
			password.append(allChars.charAt(random.nextInt(allChars.length())));
		}

		// Shuffle to avoid predictable pattern
		List<Character> passwordChars = password.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
		Collections.shuffle(passwordChars, random);

		return passwordChars.stream().map(String::valueOf).collect(Collectors.joining());
	}

	@Override
	public void saveAllBookingManagementDetails(List<BookingManagement> bookings) {
		bookingManagementRepo.saveAll(bookings);
	}

}
