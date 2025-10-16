package com.tech.user_insights.dto;

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
public class BookingManagementDto {
	private List<String> spotName;
	private Timestamp visitDate;
	private Integer numberOfPeople;
	private String bookingRefId;
	private String remarks;
}
