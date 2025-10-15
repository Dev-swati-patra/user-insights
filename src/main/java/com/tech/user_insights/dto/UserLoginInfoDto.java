package com.tech.user_insights.dto;

import lombok.Data;

@Data
public class UserLoginInfoDto {
	private String userName;
	private String password;
	private String otp;

	private String oldPassword;
	private String newPassword;
	private String confirmPassword;

	private String userPhonNumber;

	private String loginType;
}
