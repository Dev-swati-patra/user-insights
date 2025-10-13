package com.tech.user_insights.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tech.user_insights.constants.BookingStatus;
import com.tech.user_insights.constants.ServiceCode;
import com.tech.user_insights.constants.StringUtils;
import com.tech.user_insights.dto.BookingManagementDto;
import com.tech.user_insights.pojo.BookingManagement;
import com.tech.user_insights.pojo.SpotDetails;
import com.tech.user_insights.pojo.UserInfo;
import com.tech.user_insights.responsedto.BookingManagementResponseDto;
import com.tech.user_insights.responsedto.ErrorResponseDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.MasterService;
import com.tech.user_insights.service.UserManagementService;
import com.tech.user_insights.validations.ValidationUserInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {
	private final MasterService masterService;
	private final ValidationUserInfo validationUserInfo;

	@Override
	public ResponseDto createBooking_V1_0(BookingManagementDto managementDto) {
		ResponseDto response = new ResponseDto();
		BookingManagement bookingManagement = null;
		List<ErrorResponseDto> errorResponseList = new ArrayList<ErrorResponseDto>();
		if (!StringUtils.isEmpty(managementDto.getUserName()) && !StringUtils.isEmpty(managementDto.getSpotName())) {
			UserInfo userInfo = masterService.getDataByUserName(managementDto.getUserName());
			SpotDetails spotDetails = masterService.getDataBySpotName(managementDto.getSpotName());
			if (StringUtils.isValidObj(userInfo) && StringUtils.isValidObj(spotDetails)) {
				errorResponseList = validationUserInfo.validateBookManagementDetails(managementDto);
				if (errorResponseList.isEmpty()) {
					bookingManagement = new BookingManagement();
					bookingManagement.setUserInfo(userInfo);
					bookingManagement.setSpotDetails(spotDetails);
					bookingManagement.setBookingDate(null);
					bookingManagement.setVisitDate(managementDto.getVisitDate());
					bookingManagement.setNumberOfPeople(managementDto.getNumberOfPeople());
					BigDecimal total = spotDetails.getPricePerPerson()
							.multiply(BigDecimal.valueOf(managementDto.getNumberOfPeople()));
					bookingManagement.setTotalAmount(total);
					bookingManagement.setBookingStatus(BookingStatus.PENDING);
					bookingManagement.setCreatedAt(LocalDateTime.now());
					bookingManagement.setPaymentStatus(BookingStatus.PENDING);
					bookingManagement.setRemarks(null);
					bookingManagement.setUpdatedAt(null);
					masterService.saveBookingManagementDetails(bookingManagement);
					response.setStatus("SUCCESS");
				} else {
					response.setStatus("FAIL");
					response.setListErrResponse(errorResponseList);
				}
			} else {
				response.setStatus("FAIL");
				response.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage()),
								new ErrorResponseDto(ServiceCode.SVC031.getCode(), ServiceCode.SVC031.getMessage())));
			}
			bookingManagement = new BookingManagement();

		} else {
			response.setStatus("FAIL");
			response.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC029.getCode(), ServiceCode.SVC029.getMessage()),
							new ErrorResponseDto(ServiceCode.SVC030.getCode(), ServiceCode.SVC030.getMessage())));
		}
		return response;
	}

	@Override
	public ResponseDto fetchUserBooking_V1_0(BookingManagementDto managementDto) {
		ResponseDto response = new ResponseDto();
		BookingManagementResponseDto dto = null;
		if (!StringUtils.isEmpty(managementDto.getUserName())) {
			UserInfo user = masterService.getDataByUserName(managementDto.getUserName());
			BookingManagement bookManagementData = masterService.getBookManagementDataByUserId(user.getUserId());
			if (StringUtils.isValidObj(bookManagementData)) {
				dto = new BookingManagementResponseDto();
				dto.setUserName(bookManagementData.getUserInfo().getUserName());
				dto.setSpotName(bookManagementData.getSpotDetails().getSpotName());
				dto.setBookingDate(bookManagementData.getBookingDate());
				dto.setVisitDate(bookManagementData.getVisitDate());
				dto.setNumberOfPeople(bookManagementData.getNumberOfPeople());
				dto.setTotalAmount(bookManagementData.getTotalAmount());
				dto.setPaymentStatus(bookManagementData.getPaymentStatus());
				dto.setCreatedAt(bookManagementData.getCreatedAt());
				dto.setUpdatedAt(bookManagementData.getUpdatedAt());
				dto.setRemarks(bookManagementData.getRemarks());
				dto.setBookingStatus(bookManagementData.getBookingStatus());
				response.setBookingManagementResponseDto(dto);
				response.setStatus("SUCCESS");
			} else {
				response.setStatus("FAIL");
				response.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage())));
			}

		} else {
			response.setStatus("FAIL");
			response.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC029.getCode(), ServiceCode.SVC029.getMessage())));
		}
		return response;
	}

	@Override
	public ResponseDto cancelBooking_V1_0(BookingManagementDto managementDto) {
		ResponseDto response = new ResponseDto();
		if (!StringUtils.isEmpty(managementDto.getUserName())) {
			UserInfo user = masterService.getDataByUserName(managementDto.getUserName());
			BookingManagement bookManagementData = masterService.getBookManagementDataByUserId(user.getUserId());
			if (StringUtils.isValidObj(bookManagementData)) {
				bookManagementData.setBookingStatus(BookingStatus.CANCELLED);
				masterService.saveBookingManagementDetails(bookManagementData);
				response.setStatus("SUCCESS");
			} else {
				response.setStatus("FAIL");
				response.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage())));
			}

		} else {
			response.setStatus("FAIL");
			response.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC029.getCode(), ServiceCode.SVC029.getMessage())));
		}
		return response;
	}

}
