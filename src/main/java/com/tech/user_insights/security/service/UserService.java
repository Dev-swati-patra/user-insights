package com.tech.user_insights.security.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tech.user_insights.pojo.UserInfo;
import com.tech.user_insights.repo.UserInfoRepo;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserInfoRepo userInfoRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo userInfo = userInfoRepo.findByUserName(username);
		if (userInfo == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new User(userInfo.getUserName(), userInfo.getUserPassword(),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userInfo.getUserRole())));
	}

}
