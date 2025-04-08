package com.tech.user_insights.config;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tech.user_insights.constants.ServiceCode;
import com.tech.user_insights.responsedto.ErrorResponseDto;
import com.tech.user_insights.responsedto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandling {
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ResponseDto> handleNullPointerExcepion(NullPointerException ex) {
		ResponseDto response = new ResponseDto("Fail", List.of(new ErrorResponseDto("500", "Null value encounted")));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<ResponseDto> handleIndexOutOfException(IndexOutOfBoundsException ex) {
		ResponseDto response = new ResponseDto("Fail", List.of(new ErrorResponseDto("500", "Null value encounted")));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<ResponseDto> handleArithmeticException(ArithmeticException ex) {
		ResponseDto response = new ResponseDto("Fail", List.of(new ErrorResponseDto("500", "Null value encounted")));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDto> handleExcepion(Exception ex) {
		ResponseDto response = new ResponseDto("Fail", List.of(new ErrorResponseDto("500", ServiceCode.SVC100.getMessage())));
		return new ResponseEntity<ResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}
