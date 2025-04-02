package com.tech.user_insights.responsedto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseDto {
	private String status;
	List<ErrorResponseDto> listErrResponse;

}
