package com.tech.user_insights.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.user_insights.responsedto.ResponseDto;

@RestController
@RequestMapping("/auth")
public class AuthenticateController {
//	@Autowired
//	private ValidationUserInfo validationUserInfo;

	@PostMapping("/register/V1.0")
	public ResponseEntity<String> register_V1_0() {
//		validationUserInfo.validateUserInfoData(userInfoDto);

		return new ResponseEntity<>("token", HttpStatus.OK);
	}

	@PostMapping("/signIn/V1.0")
	public ResponseEntity<ResponseDto> signIn_V1_0() {

		return null;
	}

}
