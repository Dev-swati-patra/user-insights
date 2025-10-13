package com.tech.user_insights.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.user_insights.dto.SpotDetailsDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.SpotManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/spots")
@PreAuthorize("hasRole('AGENCY')")
@RequiredArgsConstructor
public class SpotManagementController {
	private final SpotManagementService spotManagementService;

	@PostMapping("/create/V1.0")
	public ResponseEntity<ResponseDto> createSpot_V1_0(@RequestBody SpotDetailsDto spotDetailsDto) {
		ResponseDto response = new ResponseDto();
		try {
			response = spotManagementService.createSpot_V1_0(spotDetailsDto);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("FAIL");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/fetch/V1.0")
	public ResponseEntity<List<SpotDetailsDto>> fetchAllSpot_V1_0() {
		List<SpotDetailsDto> list = new ArrayList<SpotDetailsDto>();
		try {
			list = spotManagementService.fetchAllSpot_V1_0();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(list, HttpStatus.OK);

	}

	@PostMapping("/fetchOne/V1.0")
	public ResponseEntity<SpotDetailsDto> fetchSingleSpot_V1_0(@RequestBody SpotDetailsDto spotDetailsDto) {
		SpotDetailsDto spot = new SpotDetailsDto();
		try {
			spot = spotManagementService.fetchSingleSpot_V1_0(spotDetailsDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(spot, HttpStatus.OK);

	}

	@PostMapping("/update/V1.0")
	public ResponseEntity<ResponseDto> updateSpot_V1_0(@RequestBody SpotDetailsDto spotDetailsDto) {
		ResponseDto response = new ResponseDto();
		try {
			response = spotManagementService.updateSpot_V1_0(spotDetailsDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/delete/V1.0")
	public ResponseEntity<ResponseDto> deleteSpot_V1_0(@RequestBody SpotDetailsDto spotDetailsDto) {
		ResponseDto response = new ResponseDto();
		try {
			response = spotManagementService.deleteSpot_V1_0(spotDetailsDto);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("FAIL");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
