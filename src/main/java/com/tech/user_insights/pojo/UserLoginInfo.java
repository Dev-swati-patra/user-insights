package com.tech.user_insights.pojo;

import java.sql.Timestamp;

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
@Table(schema = "core", name = "user_login_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "login_time")
	private Timestamp loginTime;

	@Column(name = "logout_time")
	private Timestamp logoutTime;

	@Column(name = "login_status")
	private Boolean loginStatus;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "browser")
	private String browser;

	@Column(name = "active_time")
	private Timestamp activeTime;

}
