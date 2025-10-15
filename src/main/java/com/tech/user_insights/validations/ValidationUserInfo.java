package com.tech.user_insights.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.user_insights.constants.ServiceCode;
import com.tech.user_insights.constants.StatusMessage;
import com.tech.user_insights.constants.StringUtils;
import com.tech.user_insights.dto.BookingManagementDto;
import com.tech.user_insights.dto.SpotDetailsDto;
import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.pojo.UserAgencyInfo;
import com.tech.user_insights.responsedto.ErrorResponseDto;
import com.tech.user_insights.service.MasterService;

@Component
public class ValidationUserInfo {
	@Autowired
	private MasterService masterService;

	public List<ErrorResponseDto> validateUserInfoData(UserInfoDto infoDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();

		if (StringUtils.isEmpty(infoDto.userName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC002));

		} else if (null != masterService.getDataByUserName(infoDto.userName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC003));
		}

		if (StringUtils.isEmpty(infoDto.userEmail())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC004));

		} else if (null != masterService.getDataByUserEmail(infoDto.userEmail())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC042));

		} else if (!infoDto.userEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC005));
		}

		if (StringUtils.isEmpty(infoDto.userPassword())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC006));

		} else if (!passwordValidate(infoDto.userPassword())) {

			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC007));
		}
		if (StringUtils.isEmpty(infoDto.fullName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC008));

		}
		if (StringUtils.isEmpty(infoDto.districtName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC010));

		} else if (!masterService.isValidDistrictName(infoDto.districtName(), infoDto.stateName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC011));
		}
		if (StringUtils.isEmpty(infoDto.stateName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC012));
		} else if (!masterService.isValidStateName(infoDto.stateName(), infoDto.countryName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC013));
		}
		if (StringUtils.isEmpty(infoDto.countryName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC014));
		} else if (!masterService.isValidCountryName(infoDto.countryName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC015));
		}

		if (StringUtils.isEmpty(infoDto.userAddress())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC016));
		} else if (StringUtils.matchPattern("", infoDto.userAddress())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC017));
		}
		if (!StringUtils.isValidMobile(infoDto.userPhoneNumber())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC019));
		} else if (null != masterService.getDataByUserPhoneNumber(infoDto.userPhoneNumber())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC043));
		}
		if (StringUtils.isEmpty(infoDto.userAge())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC018));
		}
//		if (StringUtils.isEmpty(infoDto.userPancard()) && StringUtils.isEmpty(infoDto.userPassport())
//				&& StringUtils.isEmpty(infoDto.userAadhar())) {
//			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC020));
//		} else if (!StringUtils.isEmpty(infoDto.userPancard())) {
//			if (!infoDto.userPancard().matches("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")) {
//				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC021));
//			}
//		} else if (!StringUtils.isEmpty(infoDto.userAadhar())) {
//			if (!infoDto.userAadhar().matches("\\d{12}")) {
//				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC022));
//			}
//
//		}
		return errorRespnse;
	}

	public boolean passwordValidate(String password) {
		return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

	}

	public List<ErrorResponseDto> validateUpdateUserprofileData(UserInfoDto userInfoDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();
		if (!StringUtils.isEmpty(userInfoDto.userEmail())) {
			if (!userInfoDto.userEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC005));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.userPassword())) {
			if (!passwordValidate(userInfoDto.userPassword())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC007));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.countryName())) {
			if (!masterService.isValidCountryName(userInfoDto.countryName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC015));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.stateName())) {
			if (StringUtils.isEmpty(userInfoDto.countryName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC014));
			} else if (!masterService.isValidStateName(userInfoDto.stateName(), userInfoDto.countryName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC013));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.districtName())) {
			if (StringUtils.isEmpty(userInfoDto.stateName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC012));
			} else if (!masterService.isValidDistrictName(userInfoDto.districtName(), userInfoDto.stateName())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC011));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.userAddress())) {
			if (!StringUtils.matchPattern("^[A-Z][a-z]*$", userInfoDto.userAddress())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC017));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.userPancard())) {
			if (!userInfoDto.userPancard().matches("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC021));
			}
		}
		if (!StringUtils.isEmpty(userInfoDto.userAadhar())) {
			if (!userInfoDto.userAadhar().matches("\\d{12}")) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC022));
			}

		}

		return errorRespnse;
	}

	public List<ErrorResponseDto> validateSpotDetails(SpotDetailsDto spotDetailsDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();
		if (StringUtils.isEmpty(spotDetailsDto.getSpotName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC030));
		} else if (null != masterService.getDataBySpotName(spotDetailsDto.getSpotName())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC036));

		}
		if (StringUtils.isEmpty(spotDetailsDto.getSpotCity())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC032));
		}

		if (StringUtils.isEmpty(spotDetailsDto.getSpotDistrict())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC010));
		} else if (!masterService.isValidDistrictName(spotDetailsDto.getSpotDistrict(),
				spotDetailsDto.getSpotState())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC011));
		}
		if (StringUtils.isEmpty(spotDetailsDto.getSpotState())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC012));
		} else if (!masterService.isValidStateName(spotDetailsDto.getSpotState(), spotDetailsDto.getSpotCountry())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC013));
		}
		if (StringUtils.isEmpty(spotDetailsDto.getSpotCountry())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC014));
		} else if (!masterService.isValidCountryName(spotDetailsDto.getSpotCountry())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC041));
		}
		if (StringUtils.isEmpty(spotDetailsDto.getOpeningTime())) {

		} else if (StringUtils.matchPattern("^([01]\\d|2[0-3]):[0-5]\\d$", spotDetailsDto.getOpeningTime())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC040));
		}
		if (StringUtils.isEmpty(spotDetailsDto.getClosingTime())) {

		} else if (StringUtils.matchPattern("^([01]\\d|2[0-3]):[0-5]\\d$", spotDetailsDto.getClosingTime())) {
			errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC040));
		}
		return errorRespnse;
	}

	public List<ErrorResponseDto> validateUpdateSpotData(SpotDetailsDto spotDetailsDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();
		if (!StringUtils.isEmpty(spotDetailsDto.getSpotDistrict())) {
			if (StringUtils.isEmpty(spotDetailsDto.getSpotState())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC012));
			} else if (!masterService.isValidDistrictName(spotDetailsDto.getSpotDistrict(),
					spotDetailsDto.getSpotState())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC011));
			}
		}
		if (!StringUtils.isEmpty(spotDetailsDto.getSpotState())) {
			if (StringUtils.isEmpty(spotDetailsDto.getSpotCountry())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC014));
			} else if (!masterService.isValidStateName(spotDetailsDto.getSpotState(),
					spotDetailsDto.getSpotCountry())) {
				errorRespnse.add(StringUtils.setErrorResponse(ServiceCode.SVC013));
			}
		}

		if (!StringUtils.isEmpty(spotDetailsDto.getSpotCountry())) {
			if (!masterService.isValidCountryName(spotDetailsDto.getSpotCountry())) {
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

	public List<ErrorResponseDto> valiadteUserAgencyData(UserInfoDto userInfoDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();
		List<UserAgencyInfo> userData = masterService.getuserFilterdData(userInfoDto.userName(),
				userInfoDto.userEmail(), Long.parseLong(userInfoDto.userPhoneNumber()), StatusMessage.UNVERIFIED.name());
		if (!StringUtils.isEmptyList(userData)) {
			
		}

//		if (StringUtils.isValidObj(userData)) {
//			if (userData.getIsActive() && "UNVERIFIED".equalsIgnoreCase(userInfoDto.approvalStatus())) {
//				errorRespnse.add(null);
//			}
//		} 
		return errorRespnse;

	}

}
