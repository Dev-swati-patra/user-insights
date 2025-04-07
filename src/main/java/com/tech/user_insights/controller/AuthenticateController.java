package com.tech.user_insights.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.user_insights.dto.ChangePasswordRequest;
import com.tech.user_insights.dto.ForgetPasswordRequest;
import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.dto.UserLoginInfoDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.AuthenticateService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticateController {

	@Autowired
	private AuthenticateService authenticateService;

	@PostMapping("/register/V1.0")
	public ResponseEntity<ResponseDto> register_V1_0(@RequestBody UserInfoDto userInfoDto) {
		ResponseDto response = new ResponseDto();
		try {
			response = authenticateService.register_V1_0(userInfoDto);

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("FAIL");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/signIn/V1.0")
	public ResponseEntity<String> signIn_V1_0(@RequestBody UserLoginInfoDto infoDto, HttpServletRequest httpRequest) {
		String token = null;
		try {
			token = authenticateService.signIn_V1_0(infoDto, httpRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(token, HttpStatus.OK);

	}

	@PostMapping("/forgetPassword/V1.0")
	public ResponseEntity<String> forgetPassword_V1_0(@RequestBody ForgetPasswordRequest request) {
		try {
			authenticateService.forgetPassword_V1_0(request);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("OTP send successfully", HttpStatus.OK);

	}
	@PostMapping("/verify_otp/V1.0")
	public ResponseEntity<String> verify_otp_V1_0(@RequestBody ForgetPasswordRequest request) {
		try {
			authenticateService.verify_otp_V1_0(request);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("OTP send successfully", HttpStatus.OK);
		
	}

	@PostMapping("/changePassword/V1.0")
	public ResponseEntity<String> changePassword_V1_0(@RequestBody ChangePasswordRequest changePasswordRequest) {
		try {
			authenticateService.changePassword_V1_0(changePasswordRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@PostMapping("/logOut/V1.0")
	public void logOut_V1_0() {

	}

}
