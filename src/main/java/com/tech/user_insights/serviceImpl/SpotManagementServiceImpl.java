package com.tech.user_insights.serviceImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tech.user_insights.constants.ServiceCode;
import com.tech.user_insights.constants.StatusMessage;
import com.tech.user_insights.constants.StringUtils;
import com.tech.user_insights.dto.SpotDetailsDto;
import com.tech.user_insights.pojo.SpotDetails;
import com.tech.user_insights.responsedto.ErrorResponseDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.MasterService;
import com.tech.user_insights.service.SpotManagementService;
import com.tech.user_insights.validations.ValidationUserInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpotManagementServiceImpl implements SpotManagementService {

	private final ValidationUserInfo validationUserInfo;
	private final MasterService masterService;
	private final ModelMapper modelMapper;

	@Override
	public ResponseDto createSpot_V1_0(SpotDetailsDto spotDetailsDto) {
		ResponseDto dto = new ResponseDto();
		SpotDetails spotDetails = null;
		List<ErrorResponseDto> errorResponseList = new ArrayList<ErrorResponseDto>();
		if (StringUtils.isValidObj(spotDetailsDto)) {
			errorResponseList = validationUserInfo.validateSpotDetails(spotDetailsDto);
			if (errorResponseList.isEmpty()) {
				spotDetails = new SpotDetails();
				spotDetails.setSpotName(spotDetailsDto.getSpotName());
				spotDetails.setSpotCity(spotDetailsDto.getSpotCity());
				spotDetails.setSpotDistrict(spotDetailsDto.getSpotDistrict());
				spotDetails.setSpotState(spotDetailsDto.getSpotState());
				spotDetails.setSpotCountry(spotDetailsDto.getSpotCountry());
				spotDetails.setIsActive(true);
				spotDetails.setPricePerPerson(spotDetailsDto.getPricePerPerson());
//				spotDetails.setOpeningTime(spotDetailsDto.getOpeningTime());
//				spotDetails.setClosingTime(spotDetailsDto.getClosingTime());
				spotDetails.setOpeningTime(LocalTime.parse(spotDetailsDto.getOpeningTime()));
				spotDetails.setClosingTime(LocalTime.parse(spotDetailsDto.getClosingTime()));
				spotDetails.setContactNumber(spotDetailsDto.getContactNumber());
				spotDetails.setEmail(spotDetailsDto.getEmail());
				spotDetails.setImages(spotDetailsDto.getImages());
				spotDetails.setAverageRating(spotDetailsDto.getAverageRating());
				spotDetails.setStatus(StatusMessage.ACTIVE);
				masterService.saveSpotDetails(spotDetails);
				dto.setStatus("SUCCESS");
			} else {
				dto.setStatus("Fail");
				dto.setListErrResponse(errorResponseList);
			}
		} else {
			dto.setStatus("FAIL");
			dto.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage())));
		}

		return dto;
	}

//	@Override
//	public List<SpotDetailsDto> fetchAllSpot_V1_0() {
//		List<SpotDetails> list = masterService.fetchAllSpot(StatusMessage.ACTIVE);
//		List<SpotDetailsDto> allSpotL = list.stream()
//				.map(spot -> new SpotDetailsDto(spot.getSpotName(), spot.getSpotCity(), spot.getSpotDistrict(),
//						spot.getSpotState(), spot.getSpotCountry(), spot.getPricePerPerson(),
//						spot.getOpeningTime().toString(), spot.getClosingTime().toString(), spot.getContactNumber(),
//						spot.getEmail(), spot.getImages(), spot.getAverageRating(), spot.getStatus().toString()))
//				.collect(Collectors.toList());
//		return allSpotL;
//	}
	
	@Override
	public List<SpotDetailsDto> fetchAllSpot_V1_0() {
		List<SpotDetails> list = masterService.fetchAllSpot(StatusMessage.ACTIVE);
		List<SpotDetailsDto> allSpotL = list.stream()
				.map(spot -> modelMapper.map(spot, SpotDetailsDto.class))
				.collect(Collectors.toList());
		return allSpotL;
		
	}

	@Override
	public SpotDetailsDto fetchSingleSpot_V1_0(SpotDetailsDto spotDetailsDto) {
		SpotDetailsDto dto = new SpotDetailsDto();
		if (!StringUtils.isEmpty(spotDetailsDto.getSpotName())) {
			SpotDetails spotDetails = masterService.getDataBySpotName(spotDetailsDto.getSpotName());
			dto = modelMapper.map(spotDetails, SpotDetailsDto.class);
		}
		return dto;
	}

	@Override
	public ResponseDto updateSpot_V1_0(SpotDetailsDto spotDetailsDto) {
		ResponseDto response = new ResponseDto();
		SpotDetails spotDetails = null;
		if (!StringUtils.isEmpty(spotDetailsDto.getSpotName())) {
			spotDetails = masterService.getDataBySpotName(spotDetailsDto.getSpotName());
			if (StringUtils.isValidObj(spotDetails)) {
				List<ErrorResponseDto> errResData = validationUserInfo.validateUpdateSpotData(spotDetailsDto);
				if (errResData.isEmpty()) {
					spotDetails
							.setSpotCity(StringUtils.isEmpty(spotDetailsDto.getSpotCity()) ? spotDetails.getSpotCity()
									: spotDetailsDto.getSpotCity());
					spotDetails.setSpotDistrict(
							StringUtils.isEmpty(spotDetailsDto.getSpotDistrict()) ? spotDetails.getSpotDistrict()
									: spotDetailsDto.getSpotDistrict());
					spotDetails.setSpotState(
							StringUtils.isEmpty(spotDetailsDto.getSpotState()) ? spotDetails.getSpotState()
									: spotDetailsDto.getSpotState());
					spotDetails.setSpotCountry(
							StringUtils.isEmpty(spotDetailsDto.getSpotCountry()) ? spotDetails.getSpotCountry()
									: spotDetailsDto.getSpotCountry());
					masterService.saveSpotDetails(spotDetails);
					response.setStatus("SUCCESS");
				} else {
					response.setStatus("FAIL");
					response.setListErrResponse(errResData);
				}

			} else {
				response.setStatus("FAIL");
				response.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC031.getCode(), ServiceCode.SVC031.getMessage())));
			}

		} else {
			response.setStatus("FAIL");
			response.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC030.getCode(), ServiceCode.SVC030.getMessage())));
		}

		return response;
	}

	@Override
	public ResponseDto deleteSpot_V1_0(SpotDetailsDto spotDetailsDto) {
		ResponseDto responseDto = new ResponseDto();
		SpotDetails spotDetails = null;
		if (StringUtils.isEmpty(spotDetailsDto.getSpotName())) {
			spotDetails = masterService.getDataBySpotName(spotDetailsDto.getSpotName());
			if (StringUtils.isValidObj(spotDetails)) {
				spotDetails.setIsActive(false);
				masterService.saveSpotDetails(spotDetails);
				responseDto.setStatus("SUCCESS");
			} else {
				responseDto.setStatus("FAIL");
				responseDto.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC031.getCode(), ServiceCode.SVC031.getMessage())));
			}

		} else {
			responseDto.setStatus("FAIL");
			responseDto.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC030.getCode(), ServiceCode.SVC030.getMessage())));
		}
		return responseDto;
	}

}
