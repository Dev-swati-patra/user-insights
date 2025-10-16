package com.tech.user_insights.responsedto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingManagementResponseDto {
	private String userName;
	private List<String> spotName;
	private Timestamp bookingDate;
	private Timestamp visitDate;
	private Integer numberOfPeople;
	private BigDecimal totalAmount;
	private String paymentStatus;
	private String remarks;
	private String bookingStatus;
	private String bookingRefId;
}
