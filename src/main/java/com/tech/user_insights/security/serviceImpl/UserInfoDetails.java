package com.tech.user_insights.security.serviceImpl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tech.user_insights.pojo.UserInfo;

public class UserInfoDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userName = null;
	String password = null;

	public UserInfoDetails(UserInfo userInfo) {

		this.userName = userInfo.getUserName();
		this.password = userInfo.getUserPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;

	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;

	}

	@Override
	public boolean isEnabled() {
		return true;

	}

}
