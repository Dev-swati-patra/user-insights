package com.tech.user_insights.dto;

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
	private Boolean isActive;
}
