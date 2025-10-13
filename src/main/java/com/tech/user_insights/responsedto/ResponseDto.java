package com.tech.user_insights.responsedto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
	private String status;
	private List<BookingManagementResponseDto> bookingManagementResponseDto;
	List<ErrorResponseDto> listErrResponse;

}
