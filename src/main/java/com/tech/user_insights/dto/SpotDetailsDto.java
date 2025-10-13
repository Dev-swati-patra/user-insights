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
    private String cityName;
    private String districtName;
    private String stateName;
    private String countryName;
    private BigDecimal pricePerPerson;
    private Time openingTime;
    private Time closingTime;
    private String contactNumber;
    private String email;
    private String images;
    private BigDecimal averageRating;
    private String status;
}
