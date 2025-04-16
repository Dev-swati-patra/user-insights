package com.tech.user_insights.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.user_insights.constants.StringUtils;
import com.tech.user_insights.dto.BookDetailsDto;
import com.tech.user_insights.pojo.BookDetails;
import com.tech.user_insights.responsedto.ErrorResponseDto;
import com.tech.user_insights.responsedto.ResponseDto;
import com.tech.user_insights.service.AdminTechService;
import com.tech.user_insights.service.MasterService;
import com.tech.user_insights.validations.RecordValidation;

@Service
public class AdminTechServiceImpl implements AdminTechService {

	@Autowired
	private RecordValidation recordValidation;

	@Autowired
	private MasterService masterService;

	@Override
	public ResponseDto addBooks_V1_0(BookDetailsDto bookDetailsDto) {
		ResponseDto dto = new ResponseDto();
		List<ErrorResponseDto> validateRecord = null;
		BookDetails bookDetails = null;
		try {
			if (StringUtils.isValidObj(bookDetailsDto)) {
				validateRecord = recordValidation.validateRecord(bookDetailsDto);
				if (validateRecord.isEmpty()) {
					bookDetails = new BookDetails();
					bookDetails.setAuthor(bookDetailsDto.getAuthor());
					bookDetails.setCategory(bookDetailsDto.getCategory());
					bookDetails.setDescription(bookDetailsDto.getDescription());
					bookDetails.setPrice(Double.parseDouble(bookDetailsDto.getPrice()));
					bookDetails.setStock(Integer.parseInt(bookDetailsDto.getStock()));
					bookDetails.setTitle(bookDetailsDto.getTitle());
					bookDetails.setImageUrl(bookDetailsDto.getImageUrl());
					masterService.saveBookDetails(bookDetails);
					dto.setStatus("SUCCESS");
				} else {
					dto.setStatus("FAIL");
					dto.setListErrResponse(validateRecord);
				}

			} else {
				dto.setStatus("FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}
//
//	@Override
//	public ResponseDto deleteBooks_v1_0(BookDetailsDto bookDetailsDto) {
//		ResponseDto responseDto = new ResponseDto();
//		try {
//			if (!StringUtils.isEmpty(bookDetailsDto.getAuthor()) && !StringUtils.isEmpty(bookDetailsDto.getTitle())) {
//				BookDetails details = masterService.getDataByAuthorAndTitle(bookDetailsDto);
//				if (StringUtils.isValidObj(details)) {
//					masterService.deleteBookDetailsData(details);
//					responseDto.setStatus("SUCCESS");
//
//				} else {
//					responseDto.setStatus("FAIL");
//					responseDto.setListErrResponse(List.of(new ErrorResponseDto(BookServiceCode.SVC012.getCode(),
//							BookServiceCode.SVC012.getMessage())));
//
//				}
//
//			} else {
//				responseDto.setStatus("FAIL");
//				responseDto.setListErrResponse(List.of(
//						new ErrorResponseDto(BookServiceCode.SVC013.getCode(), BookServiceCode.SVC013.getMessage())));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return responseDto;
//
//	}
//
//	@Override
//	public ResponseDto modifyBooks_V1_0(BookDetailsDto bookDetailsDto) {
//		ResponseDto dto = new ResponseDto();
//		BookDetails details = null;
//		try {
//			if (StringUtils.isValidObj(bookDetailsDto)) {
//				details = new BookDetails();
//				details.setTitle(bookDetailsDto.getTitle());
//				details.setAuthor(bookDetailsDto.getAuthor());
//				details.setCategory(bookDetailsDto.getCategory());
//				details.setStock(Integer.parseInt(bookDetailsDto.getStock()));
//				details.setPrice(Double.parseDouble(bookDetailsDto.getPrice()));
//				details.setDescription(bookDetailsDto.getDescription());
//				masterService.saveBookDetails(details);
//				dto.setStatus("SUCCESS");
//
//			} else {
//				dto.setStatus("FAIL");
//				dto.setListErrResponse(List.of(
//						new ErrorResponseDto(BookServiceCode.SVC013.getCode(), BookServiceCode.SVC013.getMessage())));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return dto;
//	}

}
