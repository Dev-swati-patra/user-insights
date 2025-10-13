package com.tech.user_insights.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "core",name = "district_details")
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
	
	@ManyToOne
	@JoinColumn(name = "state_code", referencedColumnName = "state_code", insertable = false, updatable = false)
	private StateDetails state;
	
//	@Column(name = "state_code")
//	private Integer stateCode;

}
