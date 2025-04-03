package com.tech.user_insights.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.user_insights.constants.ServiceCode;
import com.tech.user_insights.constants.StringUtils;
import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.responsedto.ErrorResponseDto;
import com.tech.user_insights.service.MasterService;

@Component
public class ValidationUserInfo {
	@Autowired
	private MasterService masterService;

	public List<ErrorResponseDto> validateUserInfoData(UserInfoDto infoDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();

		if (StringUtils.isEmpty(infoDto.getUserName())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC002));

		} else if (null != masterService.getDataByUserName(infoDto.getUserName())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC003));
		}

		if (StringUtils.isEmpty(infoDto.getUserEmail())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC004));

		} else if (!infoDto.getUserEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC005));
		}

		if (StringUtils.isEmpty(infoDto.getUserPassword())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC006));

		} else if (!infoDto.getUserPassword()
				.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {

			errorRespnse.add(setErrorResponse(ServiceCode.SVC007));
		}
		if (StringUtils.isEmpty(infoDto.getFullName())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC008));

		}
		if (StringUtils.isEmpty(infoDto.getDistrictName())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC010));

		} else if (!masterService.isValidDistrictName(infoDto.getDistrictName(), infoDto.getStateName())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC010));
		}
		if (StringUtils.isEmpty(infoDto.getStateName())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC012));
		} else if (!masterService.isValidStateName(infoDto.getStateName(), infoDto.getCountryName())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC013));
		}
		if (StringUtils.isEmpty(infoDto.getCountryName())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC014));
		} else if (!masterService.isValidCountryName(infoDto.getCountryName())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC015));
		}

		if (StringUtils.isEmpty(infoDto.getUserAddress())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC016));
		} else if (StringUtils.matchPattern("", infoDto.getUserAddress())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC017));
		}
		if (!StringUtils.isValidMobile(infoDto.getUserPhoneNumber())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC019));
		}
		if (StringUtils.isEmpty(infoDto.getUserAge())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC018));
		}
		if (StringUtils.isEmpty(infoDto.getUserPancard()) && StringUtils.isEmpty(infoDto.getUserPassport())
				&& StringUtils.isEmpty(infoDto.getUserAadhar())) {
			errorRespnse.add(setErrorResponse(ServiceCode.SVC020));
		} else if (!StringUtils.isEmpty(infoDto.getUserPancard())) {
			if (!infoDto.getUserPancard().matches("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")) {
				errorRespnse.add(setErrorResponse(ServiceCode.SVC021));
			}
		} else if (!StringUtils.isEmpty(infoDto.getUserAadhar())) {
			if (!infoDto.getUserAddress().matches("\\d{12}")) {
				errorRespnse.add(setErrorResponse(ServiceCode.SVC022));
			}

		}
		return errorRespnse;
	}

	private ErrorResponseDto setErrorResponse(ServiceCode serviceCode) {

		ErrorResponseDto dto = new ErrorResponseDto();
		dto.setErrorCode(serviceCode.getCode());
		dto.setErrorDesc(serviceCode.getMessage());
		return dto;

	}

}
