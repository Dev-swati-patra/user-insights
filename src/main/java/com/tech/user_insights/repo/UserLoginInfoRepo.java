package com.tech.user_insights.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.user_insights.pojo.UserLoginInfo;

@Repository
public interface UserLoginInfoRepo extends JpaRepository<UserLoginInfo, Long>{

}
