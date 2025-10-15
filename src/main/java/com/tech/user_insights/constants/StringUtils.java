package com.tech.user_insights.constants;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.tech.user_insights.responsedto.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StringUtils {

	public static boolean isValidObj(final Object inObj) {
		if (null == inObj || inObj.toString().trim().length() == 0 || inObj.toString().equalsIgnoreCase("null")) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isEmpty(final String str) {
		if ((str == null) || (str.trim().length() == 0)) {
			return true;
		} else {
			return false;
		}
	}

	public static <T> boolean isEmptyList(final List<T> inObj) {
		if (null == inObj || inObj.size() == 0) {
			return true;
		}
		return false;
	}

	public static boolean matchPattern(String regex, String data) {
		try {
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(data);
			return matcher.matches();
		} catch (Exception e) {
			log.error("Getting Exception in matchPattern: ", e);
		}
		return false;
	}

	public static boolean isValidMobile(final String inMobile) {
		Pattern pattern = Pattern.compile("[6-9][0-9]{9}");
		Matcher matcher = pattern.matcher(inMobile);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static String generateSixDigitOtp() {
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			builder.append(random.nextInt(10));
		}
		return builder.toString();
	}

	public static ErrorResponseDto setErrorResponse(ServiceCode serviceCode) {

		ErrorResponseDto dto = new ErrorResponseDto();
		dto.setErrorCode(serviceCode.getCode());
		dto.setErrorDesc(serviceCode.getMessage());
		return dto;
	}

	public static ErrorResponseDto setErrorResponse(BookServiceCode bookServiceCode) {

		ErrorResponseDto dto = new ErrorResponseDto();
		dto.setErrorCode(bookServiceCode.getCode());
		dto.setErrorDesc(bookServiceCode.getMessage());
		return dto;
	}

	public static Timestamp getCurrentTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	private static String getRandomAlphaNumeric(int length) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}

	public static String generateCustomId() {
		long timestamp = System.currentTimeMillis();
		String randomStr = getRandomAlphaNumeric(6);
		return timestamp + "-" + randomStr;
	}

}
