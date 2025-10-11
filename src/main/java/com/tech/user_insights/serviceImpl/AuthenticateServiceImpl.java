package com.tech.user_insights.serviceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import com.tech.user_insights.constants.StringUtils;
import com.tech.user_insights.dto.ChangePasswordRequest;
import com.tech.user_insights.dto.ForgetPasswordRequest;
import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.dto.UserLoginInfoDto;
import com.tech.user_insights.pojo.OtpVerification;
import com.tech.user_insights.pojo.UserInfo;
import com.tech.user_insights.pojo.UserLoginInfo;
import com.tech.user_insights.responsedto.ErrorResponseDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.security.service.JwtService;
import com.tech.user_insights.security.service.UserService;
import com.tech.user_insights.service.AuthenticateService;
import com.tech.user_insights.service.MasterService;
import com.tech.user_insights.validations.ValidationUserInfo;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
		UserInfo userInfo = null;
		List<ErrorResponseDto> errResData = validationUserInfo.validateUserInfoData(userInfoDto);
		if (errResData.isEmpty()) {
			userInfo = new UserInfo();
			userInfo.setUserName(userInfoDto.getUserName());
			userInfo.setUserEmail(userInfoDto.getUserEmail());
			userInfo.setUserPassword(passwordEncoder.encode(userInfoDto.getUserPassword()));
			userInfo.setName(userInfoDto.getFullName());
			userInfo.setUserCountryCode(masterService.getCountryCode(userInfoDto.getCountryName()));
			userInfo.setUserStateCode(masterService.getStateCode(userInfoDto.getStateName()));
			userInfo.setUserDistrictCode(masterService.getDistrictCode(userInfoDto.getDistrictName()));
			userInfo.setUserAddress(userInfoDto.getUserAddress());
			userInfo.setUserPancard(userInfoDto.getUserPancard());
			userInfo.setUserPassport(userInfoDto.getUserPassport());
			userInfo.setUserAadhar(userInfoDto.getUserAadhar());
			userInfo.setUserPhoneNumber(Long.parseLong(userInfoDto.getUserPhoneNumber()));
			userInfo.setUserAge(Integer.parseInt(userInfoDto.getUserAge()));
			userInfo.setUserRole(Role.USER);
			userInfo.setIsActive(true);
			masterService.saveUserInfoDetails(userInfo);
			responseDto.setStatus("SUCCESS");

		} else {
			responseDto.setStatus("Fail");
			responseDto.setListErrResponse(errResData);
		}
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
			loginInfo = new UserLoginInfo();
			loginInfo.setUserName(infoDto.getUserName());
			loginInfo.setLoginTime(new Timestamp(System.currentTimeMillis()));
//				loginInfo.setLogoutTime(null);
			loginInfo.setBrowser("chrome");
			loginInfo.setLoginStatus(true);
			loginInfo.setActiveTime(new Timestamp(System.currentTimeMillis()));
			loginInfo.setIpAddress(ClientInfoDetails.getClientIpAddress(request));
			masterService.saveUserLoginInfoDetails(loginInfo);

			UserDetails user = userService.loadUserByUsername(infoDto.getUserName());
			return jwtService.generateToken(user);
		} else {
			throw new UsernameNotFoundException("Invalid user request");
		}

	}

	@Override
	public ResponseDto changePassword_V1_0(ChangePasswordRequest request) {
		ResponseDto responseDto = new ResponseDto();
		UserInfo userInfoData = masterService.getDataByUserName(request.getUserName());
		if (request.getNewPassword().equals(request.getConfirmPassword())) {
			if (passwordEncoder.matches(request.getOldPassword(), userInfoData.getUserPassword())) {
				String passwordChanged = passwordEncoder.encode(request.getNewPassword());
				userInfoData.setUserPassword(passwordChanged);
				masterService.saveUserInfoDetails(userInfoData);
				responseDto.setStatus("SUCCESS");
				responseDto.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC001.getCode(), ServiceCode.SVC001.getMessage())));
			} else {
				responseDto.setStatus("FAIL");
				responseDto.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC023.getCode(), ServiceCode.SVC023.getMessage())));
			}
		} else {
			responseDto.setStatus("FAIL");
			responseDto.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC024.getCode(), ServiceCode.SVC024.getMessage())));
		}
		return responseDto;

	}

	@Override
	public ResponseDto forgetPassword_V1_0(ForgetPasswordRequest request) {
		ResponseDto responseDto = new ResponseDto();
		UserInfo data = null;
		if (!StringUtils.isEmpty(request.getUserName())) {
			data = masterService.getDataByUserName(request.getUserName());

		}
//			else if (!StringUtils.isEmpty(request.getUserEmail())) {
//				data = masterService.getDataByUSerEmail(request.getUserEmail());
//			} 
//			else {
//				responseDto.setStatus("FAIL");
//				responseDto.setListErrResponse(
//						List.of(new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage())));
//
//			}
		if (null != data) {
			String otp = StringUtils.generateOtp();
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>> OTP:---" + otp);
			OtpVerification otpVerification = masterService.getOtpVerificationData(request.getUserName());
			if (null == otpVerification)
				otpVerification = new OtpVerification();
			otpVerification.setOtp(otp);
			otpVerification.setUserEmail(data.getUserEmail());
			otpVerification.setUserName(data.getUserName());
			otpVerification.setExpiryTime(LocalDateTime.now().plusMinutes(30));
			otpVerification.setVerified(true);
			masterService.saveOtpVerificationDetails(otpVerification);
			responseDto.setStatus("OTP sent successfully");
		} else {
			responseDto.setStatus("FAIL");
			responseDto.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage())));

		}
		return responseDto;

	}

	@Override
	public ResponseDto verify_otp_V1_0(ForgetPasswordRequest request) {
		ResponseDto responseDto = new ResponseDto();
		OtpVerification otpVerification = null;
		otpVerification = masterService.getOtpVerificationData(request.getUserName());
		if (null != request.getOtp() && (request.getOtp().equals(otpVerification.getOtp())
				&& otpVerification.getExpiryTime().isAfter(LocalDateTime.now()))) {
			responseDto.setStatus("OTP verify Successfully...");
		} else {
			responseDto.setStatus("FAIL");
			responseDto.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC027.getCode(), ServiceCode.SVC027.getMessage())));

		}
		return responseDto;
	}

	@Override
	public ResponseDto resetPassword_v1_0(@RequestBody ChangePasswordRequest changePasswordRequest) {
		ResponseDto dto = new ResponseDto();
		if (null == changePasswordRequest.getNewPassword() || null == changePasswordRequest.getConfirmPassword()) {
			dto.setStatus("FAIL");
			dto.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC028.getCode(), ServiceCode.SVC028.getMessage())));
		} else if (StringUtils.isEmpty(changePasswordRequest.getUserName())) {
			dto.setStatus("FAIL");
			dto.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC029.getCode(), ServiceCode.SVC029.getMessage())));
		} else {
			if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
				log.info(changePasswordRequest.getNewPassword() + "*******"
						+ changePasswordRequest.getConfirmPassword());
				dto.setStatus("FAIL");
				dto.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC024.getCode(), ServiceCode.SVC024.getMessage())));
			} else {
				UserInfo userInfo = masterService.getDataByUserName(changePasswordRequest.getUserName());
				if (StringUtils.isValidObj(userInfo)) {
					OtpVerification otpData = masterService.getOtpVerificationData(changePasswordRequest.getUserName());
					if (StringUtils.isValidObj(otpData)) {
						if (otpData.isVerified() && otpData.getExpiryTime().isAfter(LocalDateTime.now())) {
							boolean passwordValidate = validationUserInfo
									.passwordValidate(changePasswordRequest.getNewPassword());
							if (!passwordValidate) {
								dto.setStatus("FAIL");
								dto.setListErrResponse(List.of(new ErrorResponseDto(ServiceCode.SVC007.getCode(),
										ServiceCode.SVC007.getMessage())));

							} else {
								userInfo.setUserPassword(
										passwordEncoder.encode(changePasswordRequest.getNewPassword()));
								masterService.saveUserInfoDetails(userInfo);
								dto.setStatus("SUCCESS");
								dto.setListErrResponse(List.of(new ErrorResponseDto(ServiceCode.SVC001.getCode(),
										ServiceCode.SVC001.getMessage())));
							}
						} else {
							dto.setStatus("FAIL");
							dto.setListErrResponse(List.of(new ErrorResponseDto(ServiceCode.SVC027.getCode(),
									ServiceCode.SVC027.getMessage())));

						}
					} else {
						dto.setStatus("FAIL");
						dto.setListErrResponse(List.of(
								new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage())));
					}
				} else {
					dto.setStatus("FAIL");
					dto.setListErrResponse(List
							.of(new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage())));
				}
			}
		}

		return dto;
	}

	@Override
	public ResponseDto updateUserprofile_V1_0(UserInfoDto userInfoDto) {
		ResponseDto responseDto = new ResponseDto();
		UserInfo userInfo = null;
		if (!StringUtils.isEmpty(userInfoDto.getUserName())) {
			userInfo = masterService.getDataByUserName(userInfoDto.getUserName());
			if (StringUtils.isValidObj(userInfo)) {
				List<ErrorResponseDto> errResData = validationUserInfo.validateUpdateUserprofileData(userInfoDto);
				if (errResData.isEmpty()) {
//					userInfo.setUserName(StringUtils.isEmpty(userInfoDto.getUserName()) ? userInfo.getUserName()
//							: userInfoDto.getUserName());
					userInfo.setUserEmail(StringUtils.isEmpty(userInfoDto.getUserEmail()) ? userInfo.getUserEmail()
							: userInfoDto.getUserEmail());
					userInfo.setUserPassword(
							StringUtils.isEmpty(userInfoDto.getUserPassword()) ? userInfo.getUserPassword()
									: passwordEncoder.encode(userInfoDto.getUserPassword()));
					userInfo.setName(StringUtils.isEmpty(userInfoDto.getFullName()) ? userInfo.getName()
							: userInfoDto.getFullName());
					userInfo.setUserCountryCode(
							StringUtils.isEmpty(userInfoDto.getCountryName()) ? userInfo.getUserCountryCode()
									: masterService.getCountryCode(userInfoDto.getCountryName()));
					userInfo.setUserStateCode(
							StringUtils.isEmpty(userInfoDto.getStateName()) ? userInfo.getUserStateCode()
									: masterService.getStateCode(userInfoDto.getStateName()));
					userInfo.setUserDistrictCode(
							StringUtils.isEmpty(userInfoDto.getDistrictName()) ? userInfo.getUserDistrictCode()
									: masterService.getDistrictCode(userInfoDto.getDistrictName()));
					userInfo.setUserAddress(
							StringUtils.isEmpty(userInfoDto.getUserAddress()) ? userInfo.getUserAddress()
									: userInfoDto.getUserAddress());
					userInfo.setUserPancard(
							StringUtils.isEmpty(userInfoDto.getUserPancard()) ? userInfo.getUserPancard()
									: userInfoDto.getUserPancard());
					userInfo.setUserPassport(
							StringUtils.isEmpty(userInfoDto.getUserPassport()) ? userInfo.getUserPassport()
									: userInfoDto.getUserPassport());
					userInfo.setUserAadhar(StringUtils.isEmpty(userInfoDto.getUserAadhar()) ? userInfo.getUserAadhar()
							: userInfoDto.getUserAadhar());
					userInfo.setUserPhoneNumber(
							StringUtils.isEmpty(userInfoDto.getUserPhoneNumber()) ? userInfo.getUserPhoneNumber()
									: Long.parseLong(userInfoDto.getUserPhoneNumber()));
					userInfo.setUserAge(StringUtils.isEmpty(userInfoDto.getUserAge()) ? userInfo.getUserAge()
							: Integer.parseInt(userInfoDto.getUserAge()));
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
		if (!StringUtils.isEmpty(userInfoDto.getUserName())) {
			UserInfo userInfo = masterService.getDataByUserName(userInfoDto.getUserName());
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

}
