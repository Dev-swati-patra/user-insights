package com.tech.user_insights.validations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tech.user_insights.dto.UserInfoDto;

@Component
public class ValidationUserInfo {

	public void validateUserInfoData(UserInfoDto infoDto) {
		Map<String, String> errorMap = new HashMap<String, String>();
		if (null == infoDto.getUserName() || infoDto.getUserName().isEmpty()) {

		}

	}

}
