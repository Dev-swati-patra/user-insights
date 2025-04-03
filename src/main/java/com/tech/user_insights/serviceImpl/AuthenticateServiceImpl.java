package com.tech.user_insights.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.pojo.UserInfo;
import com.tech.user_insights.repo.CountryDetailsRepo;
import com.tech.user_insights.repo.DistrictDetailsRepo;
import com.tech.user_insights.repo.StateDetailsrepo;
import com.tech.user_insights.repo.UserInfoRepo;
import com.tech.user_insights.responsedto.ErrorResponseDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.AuthenticateService;
import com.tech.user_insights.validations.ValidationUserInfo;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ValidationUserInfo validationUserInfo;
	@Autowired
	private UserInfoRepo userInfoRepo;
	@Autowired
	private CountryDetailsRepo countryDetailsRepo;
	@Autowired
	private StateDetailsrepo stateDetailsrepo;
	@Autowired
	private DistrictDetailsRepo districtDetailsRepo;

	@Override
	public ResponseDto register_V1_0(UserInfoDto userInfoDto) {
		ResponseDto responseDto = new ResponseDto();
		UserInfo userInfo = null;
		try {
			List<ErrorResponseDto> errResData = validationUserInfo.validateUserInfoData(userInfoDto);
			if (errResData.isEmpty()) {
				userInfo = new UserInfo();
				userInfo.setUserName(userInfoDto.getUserName());
				userInfo.setUserEmail(userInfoDto.getUserEmail());
				userInfo.setUserPassword(passwordEncoder.encode(userInfoDto.getUserPassword()));
				userInfo.setName(userInfoDto.getFullName());
				userInfo.setUserCountryCode(countryDetailsRepo.findByCountryName(userInfoDto.getCountryName()));
				userInfo.setUserStateCode(stateDetailsrepo.findByStateName(userInfoDto.getStateName()));
				userInfo.setUserDistrictCode(districtDetailsRepo.findByDistrictName(userInfoDto.getDistrictName()));
				userInfo.setUserAddress(userInfoDto.getUserAddress());
				userInfo.setUserPancard(userInfoDto.getUserPancard());
				userInfo.setUserPassport(userInfoDto.getUserPassport());
				userInfo.setUserAadhar(userInfoDto.getUserAadhar());
				userInfo.setUserPhoneNumber(Integer.parseInt(userInfoDto.getUserPhoneNumber()));
				userInfo.setUserAge(Integer.parseInt(userInfoDto.getUserAge()));
				userInfoRepo.save(userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseDto;

	}

}
