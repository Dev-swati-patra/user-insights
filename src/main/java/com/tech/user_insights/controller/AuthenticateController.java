package com.tech.user_insights.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.user_insights.dto.UserInfoDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.AuthenticateService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/signIn/V1.0")
	public ResponseEntity<ResponseDto> signIn_V1_0() {

		return null;
	}

}
