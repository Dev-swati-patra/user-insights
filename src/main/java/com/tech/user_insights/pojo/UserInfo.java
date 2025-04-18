package com.tech.user_insights.pojo;

import com.tech.user_insights.constants.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "user_password")
	private String userPassword;

	@Column(name = "name")
	private String name;

	@Column(name = "user_country_code")
	private Integer userCountryCode;

	@Column(name = "user_state_code")
	private Integer userStateCode;

	@Column(name = "user_district_code")
	private Integer userDistrictCode;

	@Column(name = "user_address")
	private String userAddress;

	@Column(name = "user_pancard")
	private String userPancard;

	@Column(name = "user_passport")
	private String userPassport;

	@Column(name = "user_aadhar")
	private String userAadhar;

	@Column(name = "user_phone_number")
	private Long userPhoneNumber;

	@Column(name = "user_age")
	private Integer userAge;

	@Column(name = "is_active")
	private Boolean isActive;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_role")
	private Role userRole;

}
