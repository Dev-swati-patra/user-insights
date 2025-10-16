package com.tech.user_insights.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tech.user_insights.constants.ServiceCode;
import com.tech.user_insights.constants.StatusMessage;
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
		List<ErrorResponseDto> errorResponseList = new ArrayList<ErrorResponseDto>();
		try {
			if (!StringUtils.isValidObj(managementDto)) {
				UserInfo userInfo = masterService.getDataByUserName(masterService.getUserName());
				String randomId = StringUtils.generateCustomId();
				List<BookingManagement> bookings = new ArrayList<>();
				for (String sportName : managementDto.getSpotName()) {
					SpotDetails spotDetails = masterService.getDataBySpotName(sportName);
					if (StringUtils.isValidObj(userInfo) && StringUtils.isValidObj(spotDetails)) {
						errorResponseList = validationUserInfo.validateBookManagementDetails(managementDto);
						if (errorResponseList.isEmpty()) {
							BookingManagement booking = BookingManagement.builder().bookingRefId(randomId)
									.userInfo(userInfo).spotDetails(spotDetails)
									.bookingDate(StringUtils.getCurrentTimeStamp())
									.visitDate(managementDto.getVisitDate())
									.numberOfPeople(managementDto.getNumberOfPeople())
									.totalAmount(spotDetails.getPricePerPerson()
											.multiply(BigDecimal.valueOf(managementDto.getNumberOfPeople())))
									.bookingStatus(StatusMessage.BOOKED).paymentStatus(StatusMessage.PENDING)
									.remarks(null).build();

							bookings.add(booking);
						} else {
							response.setStatus(StatusMessage.FAIL);
							response.setListErrResponse(errorResponseList);
						}
					} else {
						response.setStatus(StatusMessage.FAIL);
						response.setListErrResponse(List.of(
								new ErrorResponseDto(ServiceCode.SVC026.getCode(), ServiceCode.SVC026.getMessage()),
								new ErrorResponseDto(ServiceCode.SVC031.getCode(), ServiceCode.SVC031.getMessage())));
					}
				}
				if (!StringUtils.isEmptyList(bookings)) {
					masterService.saveAllBookingManagementDetails(bookings);
					response.setStatus(StatusMessage.SUCCESS);
				}
			} else {
				response.setStatus(StatusMessage.FAIL);
				response.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC029.getCode(), ServiceCode.SVC029.getMessage()),
								new ErrorResponseDto(ServiceCode.SVC030.getCode(), ServiceCode.SVC030.getMessage())));
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusMessage.FAIL);
			response.setListErrResponse(List
					.of(new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", "Something went wrong")));
		}
		return response;
	}

	@Override
	public ResponseDto fetchUserBooking_V1_0(BookingManagementDto managementDto) {
		ResponseDto response = new ResponseDto();
		List<BookingManagementResponseDto> bookingResponse = new ArrayList<BookingManagementResponseDto>();
		if (!StringUtils.isEmpty(masterService.getUserName())) {
			List<BookingManagement> bookings = masterService
					.getBookManagementDataByUserName(masterService.getUserName());
			Map<String, List<BookingManagement>> groupedBookings = bookings.stream()
					.collect(Collectors.groupingBy(BookingManagement::getBookingRefId));

			for (Map.Entry<String, List<BookingManagement>> entry : groupedBookings.entrySet()) {
				String bookingRefId = entry.getKey();
				List<BookingManagement> bookingList = entry.getValue();
				BookingManagement firstBooking = bookingList.get(0);
				List<String> spotNames = bookingList.stream().map(b -> b.getSpotDetails().getSpotName()).distinct()
						.collect(Collectors.toList());
				BookingManagementResponseDto dto = BookingManagementResponseDto.builder()
						.userName(firstBooking.getUserInfo().getUserName()).spotName(spotNames)
						.bookingDate(firstBooking.getBookingDate()).visitDate(firstBooking.getVisitDate())
						.numberOfPeople(firstBooking.getNumberOfPeople()).totalAmount(firstBooking.getTotalAmount())
						.bookingStatus(firstBooking.getBookingStatus()).remarks(firstBooking.getRemarks())
						.paymentStatus(firstBooking.getPaymentStatus()).bookingRefId(bookingRefId).build();
				bookingResponse.add(dto);
			}
			response.setBookingManagementResponseDto(bookingResponse);
			response.setStatus(StatusMessage.SUCCESS);
		} else {
			response.setStatus(StatusMessage.FAIL);
			response.setListErrResponse(
					List.of(new ErrorResponseDto(ServiceCode.SVC029.getCode(), ServiceCode.SVC029.getMessage())));
		}
		return response;
	}

	@Override
	public ResponseDto cancelBooking_V1_0(BookingManagementDto managementDto) {
		ResponseDto response = new ResponseDto();
		List<ErrorResponseDto> errorResponseList = new ArrayList<ErrorResponseDto>();
		try {
			if (StringUtils.isValidObj(managementDto)) {
				errorResponseList = validationUserInfo.validateCancelBookingDetails(managementDto);
				if (!StringUtils.isEmptyList(errorResponseList)) {

					List<BookingManagement> bookingList = masterService
							.getBookingDetailsByBookingRefId(managementDto.getBookingRefId());
					if (!StringUtils.isEmptyList(bookingList)) {
						bookingList.forEach(bl -> {
							bl.setBookingStatus(StatusMessage.CANCELLED);
							bl.setRemarks(managementDto.getRemarks());
						});
						masterService.saveAllBookingManagementDetails(bookingList);
						response.setStatus(StatusMessage.SUCCESS);
					} else {
						response.setStatus(StatusMessage.FAIL);
						response.setListErrResponse(List.of(
								new ErrorResponseDto(ServiceCode.SVC044.getCode(), ServiceCode.SVC044.getMessage())));
					}
				} else {
					response.setStatus(StatusMessage.FAIL);
					response.setListErrResponse(errorResponseList);
				}
			} else {
				response.setStatus(StatusMessage.FAIL);
				response.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC038.getCode(), ServiceCode.SVC038.getMessage())));
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusMessage.FAIL);
		}

		return response;
	}

	@Override
	public ResponseDto updateUserBooking_V1_0(BookingManagementDto managementDto) {
		ResponseDto response = new ResponseDto();
		try {
			if (null != managementDto && null != managementDto.getBookingRefId()) {
//				BookingManagement bookingManagement = masterService
//						.getBookingDetailsByBookingRefId(managementDto.getBookingRefId());
//				if (null != bookingManagement) {
//					bookingManagement.setNumberOfPeople(managementDto.getNumberOfPeople());
//					bookingManagement.setVisitDate(managementDto.getVisitDate());
//					bookingManagement.setBookingStatus(BookingStatus.BOOKED);
//					masterService.saveBookingManagementDetails(bookingManagement);
//					response.setStatus(StatusMessage.SUCCESS);
//				} else {
//					response.setStatus(StatusMessage.FAIL);
//					response.setListErrResponse(List
//							.of(new ErrorResponseDto(ServiceCode.SVC039.getCode(), ServiceCode.SVC039.getMessage())));
//				}

			} else {
				response.setStatus(StatusMessage.FAIL);
				response.setListErrResponse(
						List.of(new ErrorResponseDto(ServiceCode.SVC038.getCode(), ServiceCode.SVC038.getMessage())));
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusMessage.FAIL);
		}

		return response;
	}

}
