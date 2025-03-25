package com.tech.user_insights.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "country_details")
public class CountryDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long countryId;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "country_code")
	private Integer countryCode;

	@Column(name = "country_short_name")
	private String countryShortName;
}
