package com.tech.user_insights.responsedto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tech.user_insights.constants.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingManagementResponseDto {
	private String userName;
	private String spotName;
	private LocalDateTime bookingDate;
	private LocalDateTime visitDate;
	private Integer numberOfPeople;
	private BigDecimal totalAmount;
	private BookingStatus paymentStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String remarks;
	private BookingStatus bookingStatus;
}
