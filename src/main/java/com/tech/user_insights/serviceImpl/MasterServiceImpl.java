package com.tech.user_insights.serviceImpl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tech.user_insights.constants.StatusMessage;
import com.tech.user_insights.constants.StringUtils;
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
import com.tech.user_insights.service.MasterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
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
//	@Autowired
//	private BookdetailsRepo bookdetailsRepo;

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
	public UserInfo getDataByUSerEmail(String userEmail) {
		return infoRepo.findByUserEmail(userEmail);
	}

	@Override
	public void saveOtpVerificationDetails(OtpVerification otpVerification) {
		otpVerificationRepo.save(otpVerification);
	}

	@Override
	public OtpVerification getOtpVerificationData(String userName) {
		OtpVerification otpVerification = null;
//		if (!StringUtils.isEmpty(request.getUserEmail())) {
//			otpVerification = otpVerificationRepo.findByUserEmail(request.getUserEmail());
//		} else 
		if (!StringUtils.isEmpty(userName)) {
			otpVerification = otpVerificationRepo.findByUserName(userName);

		}
		return otpVerification;
	}

	@Override
	public void saveSpotDetails(SpotDetails spotDetails) {
		spotDetailsRepo.save(spotDetails);
	}

	@Override
	public List<SpotDetails> fetchAllSpot(StatusMessage active) {
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
	public List<BookingManagement> getBookManagementDataByUserId(Integer userId) {
		return bookingManagementRepo.findByUserInfo_UserId(userId);
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
	public BookingManagement getBookingDetailsById(Long bookingId) {
		return bookingManagementRepo.findById(bookingId).get();
	}

	@Override
	public void saveUserAgencyInfoDetails(UserAgencyInfo userAgencyInfo) {
		userAgencyInfoRepo.save(userAgencyInfo);

	}

	@Override
	public UserAgencyInfo getUserAgencyInfoDetails(String userName) {
		return userAgencyInfoRepo.findByUserNameAndApprovalStatus(userName, StatusMessage.UNVERIFIED.name());
	}

//	@Override
//	public void saveBookDetails(BookDetails bookDetails) {
//		bookdetailsRepo.save(bookDetails);
//	}
//
//	@Override
//	public BookDetails getDataByAuthorAndTitle(BookDetailsDto bookDetailsDto) {
//		return bookdetailsRepo.findByAuthorAndTitle(bookDetailsDto.getAuthor(), bookDetailsDto.getTitle());
////		return bookdetailsRepo.findByBookDetails(bookDetailsDto);
//	}
//
//	@Override
//	public void deleteBookDetailsData(BookDetails details) {
//		bookdetailsRepo.delete(details);
//	}
//
//	@Override
//	public BookDetails viewBookDetailsData(BookDetails details) {
//		return bookdetailsRepo.findByAuthorAndTitle(details.getAuthor(), details.getTitle());
//	}

}
