package com.tech.user_insights.dto;

import lombok.Data;

@Data
public class ForgetPasswordRequest {
//	private String userEmail;
	private String userPhonNumber;
	private String userName;
	private String otp;
}
