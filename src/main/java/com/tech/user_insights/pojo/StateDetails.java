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
@Table(name = "state_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stateId;

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "state_code")
	private Integer stateCode;

	@Column(name = "state_short_name")
	private String stateShortName;

	@ManyToOne
	@JoinColumn(name = "country_code", referencedColumnName = "country_code", insertable = false, updatable = false)
	private CountryDetails country;

//    @Column(name = "country_code")
//	private Integer countryCode;
}
