package com.tech.user_insights.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_info")
public class UserInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "user_password")
	private String userPassword;
	
	@Column(name = "name")
	private String name;

	@Column(name = "user_state_code")
	private String userStateCode;

	@Column(name = "user_district")
	private String userDistrict;

	@Column(name = "user_address")
	private String userAddress;

	@Column(name = "user_pancard")
	private String userPancard;

	@Column(name = "user_passport")
	private String userPassport;

	@Column(name = "user_country")
	private String userCountry;

	@Column(name = "user_aadhar")
	private String userAadhar;

	@Column(name = "user_phone_number")
	private Integer userPhoneNumber;

	@Column(name = "user_age")
	private Integer userAge;

	@Column(name = "is_active")
	private Boolean isActive;

}
