package com.tech.user_insights.service;

import com.tech.user_insights.dto.ChangePasswordRequest;
import com.tech.user_insights.dto.ForgetPasswordRequest;
import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.dto.UserLoginInfoDto;
import com.tech.user_insights.responsedto.ResponseDto;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticateService {
	ResponseDto register_V1_0(UserInfoDto userInfoDto);

	String signIn_V1_0(UserLoginInfoDto infoDto, HttpServletRequest httpServletRequest);

	ResponseDto changePassword_V1_0(ChangePasswordRequest changePasswordRequest);

	ResponseDto forgetPassword_V1_0(ForgetPasswordRequest request);

	ResponseDto verify_otp_V1_0(ForgetPasswordRequest request);

	ResponseDto resetPassword_v1_0(ChangePasswordRequest passwordRequest);

	ResponseDto updateUserprofile_V1_0(UserInfoDto userInfoDto);

	ResponseDto deleteUser_V1_0(UserInfoDto userInfoDto);

	ResponseDto adminApproved_V1_0(UserInfoDto userInfoDto);

}
