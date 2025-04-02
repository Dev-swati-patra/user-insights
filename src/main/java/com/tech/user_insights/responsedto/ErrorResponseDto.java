package com.tech.user_insights.responsedto;

import lombok.Data;

@Data
public class ErrorResponseDto {
	private String status;
	private String errorCode;
	private String errorDesc;

}
