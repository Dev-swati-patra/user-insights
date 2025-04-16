package com.tech.user_insights.validations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tech.user_insights.constants.BookServiceCode;
import com.tech.user_insights.constants.StringUtils;
import com.tech.user_insights.dto.BookDetailsDto;
import com.tech.user_insights.responsedto.ErrorResponseDto;

@Component
public class RecordValidation {

	public List<ErrorResponseDto> validateRecord(BookDetailsDto bookDetailsDto) {
		List<ErrorResponseDto> errorRespnse = new ArrayList<ErrorResponseDto>();

		if (StringUtils.isEmpty(bookDetailsDto.getTitle())) {
			errorRespnse.add(StringUtils.setErrorResponse(BookServiceCode.SVC002));
		} else if (!bookDetailsDto.getTitle().matches("^[A-Za-z0-9 .,'\":;!?()&\\-]{1,255}$")) {
			errorRespnse.add(StringUtils.setErrorResponse(BookServiceCode.SVC003));
		}
		if (StringUtils.isEmpty(bookDetailsDto.getAuthor())) {
			errorRespnse.add(StringUtils.setErrorResponse(BookServiceCode.SVC004));

		} else if (!bookDetailsDto.getAuthor().matches("^[A-Za-z .'-]{2,100}$")) {
			errorRespnse.add(StringUtils.setErrorResponse(BookServiceCode.SVC005));

		}
		if (StringUtils.isValidObj(bookDetailsDto.getPrice())) {
			errorRespnse.add(StringUtils.setErrorResponse(BookServiceCode.SVC006));
		} else if (Double.parseDouble(bookDetailsDto.getPrice().trim()) < 0) {
			errorRespnse.add(StringUtils.setErrorResponse(BookServiceCode.SVC007));
		}
		if (StringUtils.isValidObj(bookDetailsDto.getStock())) {
			errorRespnse.add(StringUtils.setErrorResponse(BookServiceCode.SVC008));
		}
		if (StringUtils.isValidObj(bookDetailsDto.getCategory())) {
			errorRespnse.add(StringUtils.setErrorResponse(BookServiceCode.SVC009));
		}
		if (StringUtils.isValidObj(bookDetailsDto.getDescription())) {
			errorRespnse.add(StringUtils.setErrorResponse(BookServiceCode.SVC010));
		}
		if (StringUtils.isValidObj(bookDetailsDto.getImageUrl())) {
			errorRespnse.add(StringUtils.setErrorResponse(BookServiceCode.SVC011));
		}

		return errorRespnse;

	}

}
