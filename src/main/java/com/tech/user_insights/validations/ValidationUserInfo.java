package com.tech.user_insights.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.responsedto.ErrorResponseDto;

@Component
public class ValidationUserInfo {

	public List<ErrorResponseDto> validateUserInfoData(UserInfoDto infoDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();

		if (null == infoDto.getUserName() || infoDto.getUserName().isEmpty()) {
		}
		if (null != infoDto.getUserName()) {
			if (infoDto.getUserName().matches("")) {
			}
		}
		if (isNullOrEmpty(infoDto.getUserEmail())) {

		}
		if (!infoDto.getUserEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {

		}
		if (isNullOrEmpty(infoDto.getUserPassword())) {

		}
		if (isNullOrEmpty(infoDto.getFullName())) {

		}
		if (isNullOrEmpty(infoDto.getDistrictName())) {

		}
		if (isNullOrEmpty(infoDto.getStateName())) {

		}
		if (isNullOrEmpty(infoDto.getUserAddress())) {

		}
		if (isNullOrEmpty(infoDto.getUserPancard())) {

		}
		if (isNullOrEmpty(infoDto.getUserPassport())) {

		}
		if (isNullOrEmpty(infoDto.getCountryName())) {

		}
		if (isNullOrEmpty(infoDto.getUserAadhar())) {

		}
		if (isNullOrEmpty(infoDto.getUserPhoneNumber())) {

		}
		if (isNullOrEmpty(infoDto.getUserAge())) {

		}
		return null;
	}

	private Boolean isNullOrEmpty(String str) {
		return null == str || str.trim().isEmpty();
	}

}
