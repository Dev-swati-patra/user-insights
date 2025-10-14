package com.tech.user_insights.service;

import com.tech.user_insights.dto.BookingManagementDto;
import com.tech.user_insights.responsedto.ResponseDto;

public interface UserManagementService {

	ResponseDto createBooking_V1_0(BookingManagementDto managementDto);

	ResponseDto fetchUserBooking_V1_0(BookingManagementDto managementDto);

	ResponseDto cancelBooking_V1_0(BookingManagementDto managementDto);

	ResponseDto updateUserBooking_V1_0(BookingManagementDto managementDto);

}
