package com.tech.user_insights.dto;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotDetailsDto {
	private String spotName;
    private String spotCity;
    private String spotDistrict;
    private String spotState;
    private String spotCountry;
    private BigDecimal pricePerPerson;
    private String openingTime;
    private String closingTime;
    private String contactNumber;
    private String email;
    private String images;
    private BigDecimal averageRating;
    private String status;
}
