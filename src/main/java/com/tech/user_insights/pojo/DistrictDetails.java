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
@Table(name = "district_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long districtId;

	@Column(name = "district_name")
	private String districtName;

	@Column(name = "district_code")
	private Integer districtCode;

	@Column(name = "district_short_name")
	private String districtShortName;
	
	@Column(name = "state_code")
	private Integer stateCode;

}
