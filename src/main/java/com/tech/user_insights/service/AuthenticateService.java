package com.tech.user_insights.service;

import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.responsedto.ResponseDto;

public interface AuthenticateService {
	ResponseDto register_V1_0(UserInfoDto userInfoDto);

}
