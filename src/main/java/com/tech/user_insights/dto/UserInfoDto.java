package com.tech.user_insights.dto;

public record UserInfoDto(String fullName, String userName, String userEmail, String userAge, String userPassword,
		String countryName, String stateName, String districtName, String userAddress, String userPancard,
		String userPassport, String userAadhar, String userPhoneNumber, String userRole, String approvalStatus) {
}