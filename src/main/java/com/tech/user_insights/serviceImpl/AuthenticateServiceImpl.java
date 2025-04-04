package com.tech.user_insights.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.dto.UserLoginInfoDto;
import com.tech.user_insights.pojo.UserInfo;
import com.tech.user_insights.pojo.UserLoginInfo;
import com.tech.user_insights.responsedto.ErrorResponseDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.AuthenticateService;
import com.tech.user_insights.service.MasterService;
import com.tech.user_insights.validations.ValidationUserInfo;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ValidationUserInfo validationUserInfo;

	@Autowired
	private MasterService masterService;

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
				userInfo.setUserCountryCode(masterService.getCountryCode(userInfoDto.getCountryName()));
				userInfo.setUserStateCode(masterService.getStateCode(userInfoDto.getStateName()));
				userInfo.setUserDistrictCode(masterService.getDistrictCode(userInfoDto.getDistrictName()));
				userInfo.setUserAddress(userInfoDto.getUserAddress());
				userInfo.setUserPancard(userInfoDto.getUserPancard());
				userInfo.setUserPassport(userInfoDto.getUserPassport());
				userInfo.setUserAadhar(userInfoDto.getUserAadhar());
				userInfo.setUserPhoneNumber(Long.parseLong(userInfoDto.getUserPhoneNumber()));
				userInfo.setUserAge(Integer.parseInt(userInfoDto.getUserAge()));
				masterService.saveUserInfoDetails(userInfo);
			} else {
				responseDto.setStatus("Fail");
				responseDto.setListErrResponse(errResData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseDto;

	}

	@Override
	public ResponseDto signIn_V1_0(UserLoginInfoDto infoDto) {
		ResponseDto dto = new ResponseDto();
		UserLoginInfo loginInfo = null;
		try {
			UserInfo userInfo = masterService.getDataByUserName(infoDto.getUserName());
			if (null != userInfo) {
				loginInfo = new UserLoginInfo();
				loginInfo.setUserName(infoDto.getUserName());
				loginInfo.setLoginTime(new Timestamp(System.currentTimeMillis()));
				loginInfo.setLogoutTime(null);
				loginInfo.setBrowser("");
				loginInfo.setLoginStatus(true);
				loginInfo.setActiveTime(null);
				loginInfo.setIpAddress(null);
				masterService.saveUserLoginInfoDetails(loginInfo);
			}
			else {
				dto.setStatus("Fail! User name not exist.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

}
