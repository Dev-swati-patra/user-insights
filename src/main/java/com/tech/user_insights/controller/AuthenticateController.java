package com.tech.user_insights.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.dto.UserLoginInfoDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.AuthenticateService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticateController {

	private final AuthenticateService authenticateService;

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

	@PostMapping("/changePassword/V1.0")
	public ResponseEntity<ResponseDto> changePassword_V1_0(@RequestBody UserLoginInfoDto changePasswordRequest) {
		ResponseDto dto = new ResponseDto();
		try {
			dto = authenticateService.changePassword_V1_0(changePasswordRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseDto>(dto, HttpStatus.OK);

	}

	@PostMapping("/updateUser/V1.0")
	public ResponseEntity<ResponseDto> updateUserprofile_V1_0(@RequestBody UserInfoDto userInfoDto) {
		ResponseDto response = new ResponseDto();
		try {
			response = authenticateService.updateUserprofile_V1_0(userInfoDto);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("FAIL");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/deleteUser/V1.0")
	public ResponseEntity<ResponseDto> deleteUser_V1_0(@RequestBody UserInfoDto userInfoDto) {
		ResponseDto response = new ResponseDto();
		try {
			response = authenticateService.deleteUser_V1_0(userInfoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/forgetPassword/V1.0")
	public ResponseEntity<ResponseDto> forgetPassword_V1_0(@RequestBody UserLoginInfoDto request) {
		ResponseDto response = new ResponseDto();
		try {
			response = authenticateService.forgetPassword_V1_0(request);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/adminApproved/V1.0")
	public ResponseEntity<ResponseDto> adminApproved_V1_0(@RequestBody UserInfoDto userInfoDto) {
		ResponseDto response = new ResponseDto();
		try {
			response = authenticateService.adminApproved_V1_0(userInfoDto);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("FAIL");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/logOut/V1.0")
	public void logOut_V1_0() {

	}

}
