//package com.tech.user_insights.config;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import com.tech.user_insights.constants.ServiceCode;
//import com.tech.user_insights.responsedto.ErrorResponseDto;
//import com.tech.user_insights.responsedto.ResponseDto;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@ControllerAdvice
//public class GlobalExceptionHandling {
//
//	@Autowired
//	private HttpServletRequest request;
//
//	@ExceptionHandler(NullPointerException.class)
//	public ResponseEntity<ResponseDto> handleNullPointerExcepion(NullPointerException ex) {
//		ResponseDto response = new ResponseDto("Fail", List.of(new ErrorResponseDto("500", "Null value encounted")));
//		return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
//	}
//
//	@ExceptionHandler(IndexOutOfBoundsException.class)
//	public ResponseEntity<ResponseDto> handleIndexOutOfException(IndexOutOfBoundsException ex) {
//		ResponseDto response = new ResponseDto("Fail", List.of(new ErrorResponseDto("500", "Null value encounted")));
//		return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
//	}
//
//	@ExceptionHandler(ArithmeticException.class)
//	public ResponseEntity<ResponseDto> handleArithmeticException(ArithmeticException ex) {
//		ResponseDto response = new ResponseDto("Fail", List.of(new ErrorResponseDto("500", "Null value encounted")));
//		return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
//	}
//
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ResponseDto> handleException(Exception ex) throws Exception {
//		String path = request.getRequestURI();
//		if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
//			throw ex; // Allow Spring Boot's default error handling
//		}
//
//		ResponseDto response = new ResponseDto("Fail",
//				List.of(new ErrorResponseDto("500", ServiceCode.SVC100.getMessage())));
//		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//
//}
