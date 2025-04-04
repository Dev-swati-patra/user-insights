package com.tech.user_insights.service;

import com.tech.user_insights.dto.ChangePasswordRequest;
import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.dto.UserLoginInfoDto;
import com.tech.user_insights.responsedto.ResponseDto;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticateService {
	ResponseDto register_V1_0(UserInfoDto userInfoDto);

	String signIn_V1_0(UserLoginInfoDto infoDto,HttpServletRequest httpServletRequest);

	void changePassword_V1_0(ChangePasswordRequest changePasswordRequest);

}
