package com.tech.user_insights.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.user_insights.pojo.UserAgencyInfo;

@Repository
public interface UserAgencyInfoRepo extends JpaRepository<UserAgencyInfo, Long> {
	List<UserAgencyInfo> findByUserNameAndApprovalStatus(String userName, String approvalStatus);

	List<UserAgencyInfo> findByUserNameOrUserEmailOrUserPhoneNumberOrApprovalStatus(String userName, String userEmail,
			Long userPhoneNumber, String approvalStatus);

	List<UserAgencyInfo> findByUserNameAndApprovalStatusAndIsActive(String userName, String approvalStatus,
			Boolean isActive);

	List<UserAgencyInfo> findByUserEmailAndApprovalStatusAndIsActive(String userEmail, String approvalStatus,
			Boolean isActive);

	List<UserAgencyInfo> findByUserPhoneNumberAndApprovalStatusAndIsActive(Long userPhoneNumber, String approvalStatus,
			Boolean isActive);
}
