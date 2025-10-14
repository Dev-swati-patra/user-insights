package com.tech.user_insights.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.user_insights.pojo.UserAgencyInfo;

@Repository
public interface UserAgencyInfoRepo extends JpaRepository<UserAgencyInfo, Long> {
	UserAgencyInfo findByUserNameAndApprovalStatus(String userName, String approvalStatus);

}
