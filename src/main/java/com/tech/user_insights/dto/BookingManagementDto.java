package com.tech.user_insights.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingManagementDto {
	private String spotName;
	private LocalDateTime visitDate;
	private Integer numberOfPeople;
	private Long bookingId;
}
