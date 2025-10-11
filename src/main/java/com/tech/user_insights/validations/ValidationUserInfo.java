package com.tech.user_insights.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.user_insights.constants.ServiceCode;
import com.tech.user_insights.constants.StringUtils;
import com.tech.user_insights.dto.BookingManagementDto;
import com.tech.user_insights.dto.SpotDetailsDto;
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
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC002));

		} else if (null != masterService.getDataByUserName(infoDto.getUserName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC003));
		}

		if (StringUtils.isEmpty(infoDto.getUserEmail())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC004));

		} else if (!infoDto.getUserEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC005));
		}

		if (StringUtils.isEmpty(infoDto.getUserPassword())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC006));

		} else if (!passwordValidate(infoDto.getUserPassword())) {

			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC007));
		}
		if (StringUtils.isEmpty(infoDto.getFullName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC008));

		}
		if (StringUtils.isEmpty(infoDto.getDistrictName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC010));

		} else if (!masterService.isValidDistrictName(infoDto.getDistrictName(), infoDto.getStateName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC011));
		}
		if (StringUtils.isEmpty(infoDto.getStateName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC012));
		} else if (!masterService.isValidStateName(infoDto.getStateName(), infoDto.getCountryName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC013));
		}
		if (StringUtils.isEmpty(infoDto.getCountryName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC014));
		} else if (!masterService.isValidCountryName(infoDto.getCountryName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC015));
		}

		if (StringUtils.isEmpty(infoDto.getUserAddress())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC016));
		} else if (StringUtils.matchPattern("", infoDto.getUserAddress())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC017));
		}
		if (!StringUtils.isValidMobile(infoDto.getUserPhoneNumber())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC019));
		}
		if (StringUtils.isEmpty(infoDto.getUserAge())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC018));
		}
		if (StringUtils.isEmpty(infoDto.getUserPancard()) && StringUtils.isEmpty(infoDto.getUserPassport())
				&& StringUtils.isEmpty(infoDto.getUserAadhar())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC020));
		} else if (!StringUtils.isEmpty(infoDto.getUserPancard())) {
			if (!infoDto.getUserPancard().matches("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC021));
			}
		} else if (!StringUtils.isEmpty(infoDto.getUserAadhar())) {
			if (!infoDto.getUserAadhar().matches("\\d{12}")) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC022));
			}

		}
		return errorRespnse;
	}

//	private ErrorResponseDto setErrorResponse(ServiceCode serviceCode) {
//
//		ErrorResponseDto dto = new ErrorResponseDto();
//		dto.setErrorCode(serviceCode.getCode());
//		dto.setErrorDesc(serviceCode.getMessage());
//		return dto;
//
//	}

	public boolean passwordValidate(String password) {
		return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

	}

	public List<ErrorResponseDto> validateUpdateUserprofileData(UserInfoDto userInfoDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();
		if (!StringUtils.isEmpty(userInfoDto.getUserEmail())) {
			if (!userInfoDto.getUserEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC005));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.getUserPassword())) {
			if (!passwordValidate(userInfoDto.getUserPassword())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC007));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.getCountryName())) {
			if (!masterService.isValidCountryName(userInfoDto.getCountryName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC015));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.getStateName())) {
			if (StringUtils.isEmpty(userInfoDto.getCountryName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC014));
			} else if (!masterService.isValidStateName(userInfoDto.getStateName(), userInfoDto.getCountryName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC013));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.getDistrictName())) {
			if (StringUtils.isEmpty(userInfoDto.getStateName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC012));
			} else if (!masterService.isValidDistrictName(userInfoDto.getDistrictName(), userInfoDto.getStateName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC011));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.getUserAddress())) {
			if (!StringUtils.matchPattern("^[A-Z][a-z]*$", userInfoDto.getUserAddress())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC017));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.getUserPancard())) {
			if (!userInfoDto.getUserPancard().matches("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC021));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.getUserAadhar())) {
			if (!userInfoDto.getUserAadhar().matches("\\d{12}")) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC022));
			}

		}

		return errorRespnse;
	}

	public List<ErrorResponseDto> validateSpotDetails(SpotDetailsDto spotDetailsDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();
		if (StringUtils.isEmpty(spotDetailsDto.getSpotName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC030));
		}
		if (StringUtils.isEmpty(spotDetailsDto.getCityName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC032));
		}

		if (StringUtils.isEmpty(spotDetailsDto.getDistrictName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC010));
		} else if (!masterService.isValidDistrictName(spotDetailsDto.getDistrictName(),
				spotDetailsDto.getStateName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC011));
		}
		if (StringUtils.isEmpty(spotDetailsDto.getStateName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC012));
		} else if (!masterService.isValidStateName(spotDetailsDto.getStateName(), spotDetailsDto.getCountryName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC013));
		}
		if (StringUtils.isEmpty(spotDetailsDto.getCountryName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC014));
		} else if (!masterService.isValidCountryName(spotDetailsDto.getCountryName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC015));
		}
		return errorRespnse;
	}

	public List<ErrorResponseDto> validateUpdateSpotData(SpotDetailsDto spotDetailsDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();
		if (!StringUtils.isEmpty(spotDetailsDto.getDistrictName())) {
			if (StringUtils.isEmpty(spotDetailsDto.getStateName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC012));
			} else if (!masterService.isValidDistrictName(spotDetailsDto.getDistrictName(),
					spotDetailsDto.getStateName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC011));
			}
		}
		if (!StringUtils.isEmpty(spotDetailsDto.getStateName())) {
			if (StringUtils.isEmpty(spotDetailsDto.getCountryName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC014));
			} else if (!masterService.isValidStateName(spotDetailsDto.getStateName(),
					spotDetailsDto.getCountryName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC013));
			}
		}

		if (!StringUtils.isEmpty(spotDetailsDto.getCountryName())) {
			if (!masterService.isValidCountryName(spotDetailsDto.getCountryName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC015));
			}
		}
		return errorRespnse;
	}

	public List<ErrorResponseDto> validateBookManagementDetails(BookingManagementDto managementDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();
		if (StringUtils.isEmpty(String.valueOf(managementDto.getNumberOfPeople()))) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC033));
		} else if (managementDto.getNumberOfPeople() <= 0) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC034));
		}
		if (StringUtils.isEmpty(String.valueOf(managementDto.getVisitDate()))) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC035));
		}

		return errorRespnse;
	}

}
