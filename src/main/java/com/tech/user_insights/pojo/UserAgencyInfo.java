package com.tech.user_insights.pojo;

import java.sql.Timestamp;

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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "core", name = "user_agency_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAgencyInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "user_password")
	private String userPassword;

	@Column(name = "user_full_name")
	private String userFullName;

	@Column(name = "user_country_code")
	private Integer userCountryCode;

	@Column(name = "user_state_code")
	private Integer userStateCode;

	@Column(name = "user_district_code")
	private Integer userDistrictCode;

	@Column(name = "user_address", columnDefinition = "TEXT")
	private String userAddress;

	@Column(name = "user_phone_number")
	private Long userPhoneNumber;

	@Column(name = "is_active")
	private Boolean isActive;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_role")
	private Role userRole;

	@Column(name = "approval_status")
	private String approvalStatus;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

}
