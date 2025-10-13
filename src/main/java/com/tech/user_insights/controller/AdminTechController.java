package com.tech.user_insights.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.user_insights.dto.BookDetailsDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.AdminTechService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/admin")
public class AdminTechController {

	@Autowired
	private AdminTechService adminTechService;

	@PostMapping("/addBooks/V1.0")
	public ResponseEntity<ResponseDto> addBooks_V1_0(@RequestBody BookDetailsDto bookDetailsDto) {
		ResponseDto responseDto = new ResponseDto();
		try {
			responseDto = adminTechService.addBooks_V1_0(bookDetailsDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}

	@PostMapping("/deleteBooks/V1.0")
	public ResponseEntity<ResponseDto> deleteBooks_v1_0(@RequestBody BookDetailsDto bookDetailsDto) {
		ResponseDto dto = new ResponseDto();
		try {
			dto = adminTechService.deleteBooks_v1_0(bookDetailsDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseDto>(dto, HttpStatus.OK);

	}

	@PostMapping("/modifyBooks/V1.0")
	public ResponseEntity<ResponseDto> modifyBooks_V1_0(@RequestBody BookDetailsDto bookDetailsDto) {
		ResponseDto dto = new ResponseDto();
		try {
			dto = adminTechService.modifyBooks_V1_0(bookDetailsDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseDto>(dto, HttpStatus.OK);
	}

	@PostMapping("/viewDetails/V1.0")
	public ResponseEntity<ResponseDto> viewDetails_V1_0(@RequestBody BookDetailsDto bookDetailsDto) {
		ResponseDto dto = new ResponseDto();
		try {
			dto = adminTechService.viewDetails_V1_0(bookDetailsDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseDto>(dto, HttpStatus.OK);
	}
}
