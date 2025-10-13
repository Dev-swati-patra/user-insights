package com.tech.user_insights.serviceImpl;

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
				spotDetails.setSpotCity(spotDetailsDto.getCityName());
				spotDetails.setSpotDistrict(spotDetailsDto.getDistrictName());
				spotDetails.setSpotState(spotDetailsDto.getStateName());
				spotDetails.setSpotCountry(spotDetailsDto.getCountryName());
//				spotDetails.setIsActive(spotDetailsDto.getIsActive());
				spotDetails.setPricePerPerson(spotDetailsDto.getPricePerPerson());
				spotDetails.setOpeningTime(spotDetailsDto.getOpeningTime());
				spotDetails.setClosingTime(spotDetailsDto.getClosingTime());
				spotDetails.setContactNumber(spotDetailsDto.getContactNumber());
				spotDetails.setEmail(spotDetailsDto.getEmail());
				spotDetails.setImages(spotDetailsDto.getImages());
				spotDetails.setAverageRating(spotDetailsDto.getAverageRating());
				spotDetails.setStatus(StatusMessage.ACTIVE);
				spotDetails.setCreatedAt(spotDetailsDto.getCreatedAt());
				spotDetails.setUpdatedAt(spotDetailsDto.getUpdatedAt());
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

	@Override
	public List<SpotDetailsDto> fetchAllSpot_V1_0() {
		List<SpotDetails> list = masterService.fetchAllSpot();
		List<SpotDetailsDto> allSpotL = list.stream()
				.map(spot -> new SpotDetailsDto(spot.getSpotName(), spot.getSpotCity(), spot.getSpotDistrict(),
						spot.getSpotState(), spot.getSpotCountry(), spot.getIsActive(), spot.getPricePerPerson(),
						spot.getOpeningTime(), spot.getClosingTime(), spot.getContactNumber(), spot.getEmail(),
						spot.getImages(), spot.getAverageRating(), StatusMessage.ACTIVE.toString(), spot.getCreatedAt(),
						spot.getUpdatedAt()))
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
							.setSpotCity(StringUtils.isEmpty(spotDetailsDto.getCityName()) ? spotDetails.getSpotCity()
									: spotDetailsDto.getCityName());
					spotDetails.setSpotDistrict(
							StringUtils.isEmpty(spotDetailsDto.getDistrictName()) ? spotDetails.getSpotDistrict()
									: spotDetailsDto.getDistrictName());
					spotDetails.setSpotState(
							StringUtils.isEmpty(spotDetailsDto.getStateName()) ? spotDetails.getSpotState()
									: spotDetailsDto.getStateName());
					spotDetails.setSpotCountry(
							StringUtils.isEmpty(spotDetailsDto.getCountryName()) ? spotDetails.getSpotCountry()
									: spotDetailsDto.getCountryName());
					spotDetails.setIsActive(true);
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
				responseDto.setStatus("SUCCESS");
				masterService.saveSpotDetails(spotDetails);
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
