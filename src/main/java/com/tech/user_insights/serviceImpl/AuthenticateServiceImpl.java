package com.tech.user_insights.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.user_insights.constants.ClientInfoDetails;
import com.tech.user_insights.constants.Role;
import com.tech.user_insights.constants.ServiceCode;
import com.tech.user_insights.constants.StatusMessage;
import com.tech.user_insights.constants.StringUtils;
import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.dto.UserLoginInfoDto;
import com.tech.user_insights.pojo.UserAgencyInfo;
import com.tech.user_insights.pojo.UserInfo;
import com.tech.user_insights.pojo.UserLoginInfo;
import com.tech.user_insights.responsedto.ErrorResponseDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.security.service.JwtService;
import com.tech.user_insights.security.service.UserService;
import com.tech.user_insights.service.AuthenticateService;
import com.tech.user_insights.service.MasterService;
import com.tech.user_insights.validations.ValidationUserInfo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final ValidationUserInfo validationUserInfo;
	private final MasterService masterService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@Override
	public ResponseDto register_V1_0(UserInfoDto userInfoDto) {
		ResponseDto responseDto = new ResponseDto();

		List<ErrorResponseDto> errResData = validationUserInfo.validateUserInfoData(userInfoDto);
		if (!errResData.isEmpty()) {
			responseDto.setStatus(StatusMessage.FAIL.name());
			responseDto.setListErrResponse(errResData);
			return responseDto;
		}

		String encodedPassword = passwordEncoder.encode(userInfoDto.userPassword());
		Integer countryCode = masterService.getCountryCode(userInfoDto.countryName());
		Integer stateCode = masterService.getStateCode(userInfoDto.stateName());
		Integer districtCode = masterService.getDistrictCode(userInfoDto.districtName());
		long phoneNumber = Long.parseLong(userInfoDto.userPhoneNumber());

		if (Role.AGENCY.name().equalsIgnoreCase(userInfoDto.userRole())) {
			List<ErrorResponseDto> errorAgencyData = validationUserInfo.valiadteUserAgencyData(userInfoDto);
			if (!errorAgencyData.isEmpty()) {
				responseDto.setStatus(StatusMessage.FAIL.name());
				responseDto.setListErrResponse(errResData);
				return responseDto;
			}
			UserAgencyInfo userAgencyInfo = UserAgencyInfo.builder().userName(userInfoDto.userName())
					.userEmail(userInfoDto.userEmail()).userPassword(encodedPassword)
					.userFullName(userInfoDto.fullName()).userCountryCode(countryCode).userStateCode(stateCode)
					.userDistrictCode(districtCode).userAddress(userInfoDto.userAddress()).userPhoneNumber(phoneNumber)
					.isActive(true).userRole(Role.AGENCY).approvalStatus(StatusMessage.UNVERIFIED.name())
					.createdAt(StringUtils.getCurrentTimeStamp()).build();
			masterService.saveUserAgencyInfoDetails(userAgencyInfo);
		} else {
			UserInfo userInfo = UserInfo.builder().userName(userInfoDto.userName()).userEmail(userInfoDto.userEmail())
					.userPassword(encodedPassword).name(userInfoDto.fullName()).userCountryCode(countryCode)
					.userStateCode(stateCode).userDistrictCode(districtCode).userAddress(userInfoDto.userAddress())
					.userPhoneNumber(phoneNumber).userAge(Integer.parseInt(userInfoDto.userAge())).userRole(Role.USER)
					.isActive(true).build();
			masterService.saveUserInfoDetails(userInfo);
		}
		responseDto.setStatus(StatusMessage.SUCCESS.name());
		return responseDto;
	}

	@Override
	public String signIn_V1_0(UserLoginInfoDto infoDto, HttpServletRequest request) {
		UserLoginInfo loginInfo = null;
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(infoDto.getUserName(), infoDto.getPassword()));
		if (authentication.isAuthenticated()) {
			UserInfo userInfo = masterService.getDataByUserName(infoDto.getUserName());
			if (userInfo == null || !userInfo.getIsActive()) {
				throw new UsernameNotFoundException("User is inactive or not found");
			}
			if (StringUtils.isValidObj(infoDto.getLoginType())
					&& infoDto.getLoginType().equalsIgnoreCase(StatusMessage.SEND_OTP.name())) {
				ResponseDto sendOtpToUser = masterService.sendOtpToUser(infoDto.getUserName());
				return sendOtpToUser.getMessage();
			} else {
				if (masterService.verifyOtp(infoDto)) {
					UserDetails user = userService.loadUserByUsername(infoDto.getUserName());
					String token = jwtService.generateToken(user);
					if (StringUtils.isValidObj(token)) {
						loginInfo = new UserLoginInfo();
						loginInfo.setUserName(infoDto.getUserName());
						loginInfo.setLoginTime(StringUtils.getCurrentTimeStamp());
						loginInfo.setBrowser(ClientInfoDetails.getClientBrowser(request));
						loginInfo.setLoginStatus(true);
						loginInfo.setActiveTime(new Timestamp(System.currentTimeMillis()));
						loginInfo.setIpAddress(ClientInfoDetails.getClientIpAddress(request));
						masterService.saveUserLoginInfoDetails(loginInfo);
					}
					return token;
				}
				return "Invalid OTP";
			}

		} else {
			throw new UsernameNotFoundException("Invalid user name and password.");
		}
	}

	@Override
	public ResponseDto changePassword_V1_0(UserLoginInfoDto request) {
		ResponseDto responseDto = new ResponseDto();
		UserInfo userInfoData = masterService.getDataByUserName(request.getUserName());
		if (request.getNewPassword().equals(request.getConfirmPassword())) {
			if (passwordEncoder.matches(request.getOldPassword(), userInfoData.getUserPassword())) {
				String passwordChanged = passwordEncoder.encode(request.getNewPassword());
				userInfoData.setUserPassword(passwordChanged);
				masterService.saveUserInfoDetails(userInfoData);
				responseDto.setStatus(StatusMessage.SUCCESS.name());
				responseDto.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC001.getCode(), ServiceCode.SVC001.getMessage())));
			} else {
				responseDto.setStatus(StatusMessage.FAIL.name());
				responseDto.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC023.getCode(), ServiceCode.SVC023.getMessage())));
			}
		} else {
			responseDto.setStatus(StatusMessage.FAIL.name());
			responseDto.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC024.getCode(), ServiceCode.SVC024.getMessage())));
		}
		return responseDto;

	}

	@Override
	public ResponseDto forgetPassword_V1_0(UserLoginInfoDto request) {
		ResponseDto responseDto = new ResponseDto();
		UserInfo data = null;
		if (!StringUtils.isEmpty(request.getUserName())) {
			data = masterService.getDataByUserName(request.getUserName());
		}
		if (null != data) {
			if (StringUtils.isValidObj(request.getLoginType())
					&& request.getLoginType().equalsIgnoreCase(StatusMessage.SEND_OTP.name())) {
				ResponseDto sendOtpToUser = masterService.sendOtpToUser(request.getUserName());
				return sendOtpToUser;
			} else {
				if (masterService.verifyOtp(request)) {
					if (null == request.getNewPassword() || null == request.getConfirmPassword()) {
						responseDto.setStatus(StatusMessage.FAIL.name());
						responseDto.setListErrResponse(List.of(
								new ErrorResponseDto(ServiceCode.SVC028.getCode(), ServiceCode.SVC028.getMessage())));
					} else if (StringUtils.isEmpty(request.getUserName())) {
						responseDto.setStatus(StatusMessage.FAIL.name());
						responseDto.setListErrResponse(List.of(
								new ErrorResponseDto(ServiceCode.SVC029.getCode(), ServiceCode.SVC029.getMessage())));
					} else {

						if (!request.getNewPassword().equals(request.getConfirmPassword())) {
							log.info(request.getNewPassword() + "*******" + request.getConfirmPassword());
							responseDto.setStatus(StatusMessage.FAIL.name());
							responseDto.setListErrResponse(List.of(new ErrorResponseDto(ServiceCode.SVC024.getCode(),
									ServiceCode.SVC024.getMessage())));
						} else {
							UserInfo userInfo = masterService.getDataByUserName(request.getUserName());
							boolean passwordValidate = validationUserInfo.passwordValidate(request.getNewPassword());
							if (!passwordValidate) {
								responseDto.setStatus(StatusMessage.FAIL.name());
								responseDto
										.setListErrResponse(List.of(new ErrorResponseDto(ServiceCode.SVC007.getCode(),
												ServiceCode.SVC007.getMessage())));

							} else {
								userInfo.setUserPassword(passwordEncoder.encode(request.getNewPassword()));
								masterService.saveUserInfoDetails(userInfo);
								responseDto.setStatus(StatusMessage.SUCCESS.name());
								responseDto
										.setListErrResponse(List.of(new ErrorResponseDto(ServiceCode.SVC001.getCode(),
												ServiceCode.SVC001.getMessage())));
							}

						}

					}

				} else {
					responseDto.setStatus(StatusMessage.FAIL.name());
					responseDto.setListErrResponse(List
							.of(new ErrorResponseDto(ServiceCode.SVC027.getCode(), ServiceCode.SVC027.getMessage())));
				}
			}

		} else {
			responseDto.setStatus(StatusMessage.FAIL.name());
			responseDto.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage())));
		}
		return responseDto;

	}

	@Override
	public ResponseDto updateUserprofile_V1_0(UserInfoDto userInfoDto) {
		ResponseDto responseDto = new ResponseDto();
		UserInfo userInfo = null;
		if (!StringUtils.isEmpty(userInfoDto.userName())) {
			userInfo = masterService.getDataByUserName(userInfoDto.userName());
			if (StringUtils.isValidObj(userInfo)) {
				List<ErrorResponseDto> errResData = validationUserInfo.validateUpdateUserprofileData(userInfoDto);
				if (errResData.isEmpty()) {
//					userInfo.setUserName(StringUtils.isEmpty(userInfoDto.getUserName()) ? userInfo.getUserName()
//							: userInfoDto.getUserName());
					userInfo.setUserEmail(StringUtils.isEmpty(userInfoDto.userEmail()) ? userInfo.getUserEmail()
							: userInfoDto.userEmail());
					userInfo.setUserPassword(
							StringUtils.isEmpty(userInfoDto.userPassword()) ? userInfo.getUserPassword()
									: passwordEncoder.encode(userInfoDto.userPassword()));
					userInfo.setName(
							StringUtils.isEmpty(userInfoDto.fullName()) ? userInfo.getName() : userInfoDto.fullName());
					userInfo.setUserCountryCode(
							StringUtils.isEmpty(userInfoDto.countryName()) ? userInfo.getUserCountryCode()
									: masterService.getCountryCode(userInfoDto.countryName()));
					userInfo.setUserStateCode(StringUtils.isEmpty(userInfoDto.stateName()) ? userInfo.getUserStateCode()
							: masterService.getStateCode(userInfoDto.stateName()));
					userInfo.setUserDistrictCode(
							StringUtils.isEmpty(userInfoDto.districtName()) ? userInfo.getUserDistrictCode()
									: masterService.getDistrictCode(userInfoDto.districtName()));
					userInfo.setUserAddress(StringUtils.isEmpty(userInfoDto.userAddress()) ? userInfo.getUserAddress()
							: userInfoDto.userAddress());
//					userInfo.setUserPancard(StringUtils.isEmpty(userInfoDto.userPancard()) ? userInfo.getUserPancard()
//							: userInfoDto.userPancard());
//					userInfo.setUserPassport(
//							StringUtils.isEmpty(userInfoDto.userPassport()) ? userInfo.getUserPassport()
//									: userInfoDto.userPassport());
//					userInfo.setUserAadhar(StringUtils.isEmpty(userInfoDto.userAadhar()) ? userInfo.getUserAadhar()
//							: userInfoDto.userAadhar());
					userInfo.setUserPhoneNumber(
							StringUtils.isEmpty(userInfoDto.userPhoneNumber()) ? userInfo.getUserPhoneNumber()
									: Long.parseLong(userInfoDto.userPhoneNumber()));
					userInfo.setUserAge(StringUtils.isEmpty(userInfoDto.userAge()) ? userInfo.getUserAge()
							: Integer.parseInt(userInfoDto.userAge()));
					userInfo.setUserRole(Role.USER);
					userInfo.setIsActive(true);
					masterService.saveUserInfoDetails(userInfo);
					responseDto.setStatus("SUCCESS");
				} else {
					responseDto.setStatus("FAIL");
					responseDto.setListErrResponse(errResData);
				}

			} else {
				responseDto.setStatus("FAIL");
				responseDto.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage())));
			}

		} else {
			responseDto.setStatus("FAIL");
			responseDto.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC029.getCode(), ServiceCode.SVC029.getMessage())));
		}
		return responseDto;
	}

	@Override
	public ResponseDto deleteUser_V1_0(UserInfoDto userInfoDto) {
		ResponseDto responseDto = new ResponseDto();
		if (!StringUtils.isEmpty(userInfoDto.userName())) {
			UserInfo userInfo = masterService.getDataByUserName(userInfoDto.userName());
			if (StringUtils.isValidObj(userInfo)) {
				userInfo.setIsActive(false);
				masterService.saveUserInfoDetails(userInfo);
				responseDto.setStatus("SUCCESS");
			} else {
				responseDto.setStatus("FAIL");
				responseDto.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage())));
			}

		} else {
			responseDto.setStatus("FAIL");
			responseDto.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC029.getCode(), ServiceCode.SVC029.getMessage())));
		}
		return responseDto;
	}

	@Override
	public ResponseDto adminApproved_V1_0(UserInfoDto userInfoDto) {
		if (!StringUtils.isEmpty(userInfoDto.userName())) {
//			UserAgencyInfo userAgencyDetails = masterService.getUserAgencyInfoDetails(userInfoDto.userName());
		}
		return null;
	}

}
