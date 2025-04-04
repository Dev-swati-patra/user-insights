package com.tech.user_insights.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private String userName;

}
