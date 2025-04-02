//package com.tech.user_insights.validations;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.tech.user_insights.dto.UserInfoDto;
//import com.tech.user_insights.responsedto.ErrorResponseDto;
//
//@Component
//public class ValidationUserInfo {
//
//	@Autowired
//	private ErrorResponseDto errorResponseDto;
//
//	public void validateUserInfoData(UserInfoDto infoDto) {
//
//		if (null == infoDto.getUserName() || infoDto.getUserName().isEmpty()) {
//			errorResponseDto.setErrorCode(null);
//		}
//		if (null != infoDto.getUserName()) {
//			if (infoDto.getUserName().matches("")) {
//				errorResponseDto.setErrorCode(null);
//			}
//		}
//		if (isNullOrEmpty(infoDto.getUserEmail())) {
//
//		}
//		if (!infoDto.getUserEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
//
//		}
//		if (isNullOrEmpty(infoDto.getUserPassword())) {
//
//		}
//		if (isNullOrEmpty(infoDto.getFullName())) {
//
//		}
//		if (isNullOrEmpty(infoDto.getUserDistrictCode())) {
//
//		}
//		if (isNullOrEmpty(infoDto.getUserStateCode())) {
//
//		}
//		if (isNullOrEmpty(infoDto.getUserAddress())) {
//
//		}
//		if (isNullOrEmpty(infoDto.getUserPancard())) {
//
//		}
//		if (isNullOrEmpty(infoDto.getUserPassport())) {
//
//		}
//		if (isNullOrEmpty(infoDto.getUserCountry())) {
//
//		}
//		if (isNullOrEmpty(infoDto.getUserAadhar())) {
//
//		}
//		if (isNullOrEmpty(infoDto.getUserPhoneNumber())) {
//
//		}
//		if (isNullOrEmpty(infoDto.getUserAge())) {
//
//		}
//	}
//
//	private Boolean isNullOrEmpty(String str) {
//		return null == str || str.trim().isEmpty();
//	}
//
//}
