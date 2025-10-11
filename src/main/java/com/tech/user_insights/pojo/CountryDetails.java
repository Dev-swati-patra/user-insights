package com.tech.user_insights.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "core",name = "country_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
