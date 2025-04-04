package com.tech.user_insights.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.user_insights.constants.ClientInfoDetails;
import com.tech.user_insights.constants.ServiceCode;
import com.tech.user_insights.dto.ChangePasswordRequest;
import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.dto.UserLoginInfoDto;
import com.tech.user_insights.pojo.UserInfo;
import com.tech.user_insights.pojo.UserLoginInfo;
import com.tech.user_insights.responsedto.ErrorResponseDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.security.service.JwtService;
import com.tech.user_insights.service.AuthenticateService;
import com.tech.user_insights.service.MasterService;
import com.tech.user_insights.validations.ValidationUserInfo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ValidationUserInfo validationUserInfo;

	@Autowired
	private MasterService masterService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

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
				responseDto.setStatus("SUCCESS");

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
	public String signIn_V1_0(UserLoginInfoDto infoDto, HttpServletRequest request) {
		UserLoginInfo loginInfo = null;
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(infoDto.getUserName(), infoDto.getPassword()));
		if (authentication.isAuthenticated()) {
			UserInfo userInfo = masterService.getDataByUserName(infoDto.getUserName());
			if (null != userInfo) {
				loginInfo = new UserLoginInfo();
				loginInfo.setUserName(infoDto.getUserName());
				loginInfo.setLoginTime(new Timestamp(System.currentTimeMillis()));
//				loginInfo.setLogoutTime(null);
				loginInfo.setBrowser("chrome");
				loginInfo.setLoginStatus(true);
				loginInfo.setActiveTime(null);
				loginInfo.setIpAddress(ClientInfoDetails.getClientIpAddress(request));
				masterService.saveUserLoginInfoDetails(loginInfo);
			}
			return jwtService.generateToken(infoDto.getUserName());
		} else {
			throw new UsernameNotFoundException("Invalid user request");
		}

	}

	@Override
	public void changePassword_V1_0(ChangePasswordRequest request) {
		ErrorResponseDto responseDto = new ErrorResponseDto();
		try {
			UserInfo userInfoData = masterService.getDataByUserName(request.getUserName());
			if (request.getNewPassword().equals(request.getConfirmPassword())) {
				if (userInfoData.getUserPassword().equals(passwordEncoder.encode(request.getOldPassword()))) {
					String passwordChanged = passwordEncoder.encode(request.getNewPassword());
					UserInfo dataByUserName = masterService.getDataByUserName(request.getUserName());
					dataByUserName.setUserPassword(passwordChanged);
				} else {
					responseDto.setErrorCode(ServiceCode.SVC023.getCode());
					responseDto.setErrorDesc(ServiceCode.SVC023.getMessage());
				}
			} else {
				responseDto.setErrorCode(ServiceCode.SVC024.getCode());
				responseDto.setErrorDesc(ServiceCode.SVC024.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
