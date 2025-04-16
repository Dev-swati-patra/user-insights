package com.tech.user_insights.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.user_insights.pojo.UserInfo;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {

	UserInfo findByUserName(String username);

	UserInfo findByUserEmail(String userEmail);

}
