package com.tech.user_insights.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.user_insights.dto.BookingManagementDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.UserManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserManagementController {

	private final UserManagementService userManagementService;

	@PostMapping("/bookings/create/V1.0")
	public ResponseEntity<ResponseDto> createBooking_V1_0(@RequestBody BookingManagementDto managementDto) {
		ResponseDto responseDto = new ResponseDto();
		try {
			responseDto = userManagementService.createBooking_V1_0(managementDto);
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setStatus("FAIL");
		}
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
	@PostMapping("/bookings/fetch/V1.0")
	public ResponseEntity<ResponseDto> fetchUserBooking_V1_0(@RequestBody BookingManagementDto managementDto) {
		ResponseDto responseDto = new ResponseDto();
		try {
			responseDto = userManagementService.fetchUserBooking_V1_0(managementDto);
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setStatus("FAIL");
		}
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
	@PostMapping("/bookings/cancel/V1.0")
	public ResponseEntity<ResponseDto> cancelBooking_V1_0(@RequestBody BookingManagementDto managementDto) {
		ResponseDto responseDto = new ResponseDto();
		try {
			responseDto = userManagementService.cancelBooking_V1_0(managementDto);
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setStatus("FAIL");
		}
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
	

}
