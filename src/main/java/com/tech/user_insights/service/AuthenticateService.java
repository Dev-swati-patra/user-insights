package com.tech.user_insights.service;

import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.dto.UserLoginInfoDto;
import com.tech.user_insights.responsedto.ResponseDto;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticateService {
	ResponseDto register_V1_0(UserInfoDto userInfoDto);

	String signIn_V1_0(UserLoginInfoDto infoDto, HttpServletRequest httpServletRequest);

	ResponseDto changePassword_V1_0(UserLoginInfoDto changePasswordRequest);

	ResponseDto forgetPassword_V1_0(UserLoginInfoDto request);

	ResponseDto updateUserprofile_V1_0(UserInfoDto userInfoDto);

	ResponseDto deleteUser_V1_0(UserInfoDto userInfoDto);

	ResponseDto adminApproved_V1_0(UserInfoDto userInfoDto);

}
