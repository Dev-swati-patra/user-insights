package com.tech.user_insights.service;

import java.util.List;

import com.tech.user_insights.dto.SpotDetailsDto;
import com.tech.user_insights.responsedto.ResponseDto;

public interface SpotManagementService {

	ResponseDto createSpot_V1_0(SpotDetailsDto spotDetailsDto);

	List<SpotDetailsDto> fetchAllSpot_V1_0();

	SpotDetailsDto fetchSingleSpot_V1_0(SpotDetailsDto spotDetailsDto);

	ResponseDto updateSpot_V1_0(SpotDetailsDto spotDetailsDto);

	ResponseDto deleteSpot_V1_0(SpotDetailsDto spotDetailsDto);

}
