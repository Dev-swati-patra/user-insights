package com.tech.user_insights.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.user_insights.pojo.OtpVerification;

@Repository
public interface OtpVerificationRepo extends JpaRepository<OtpVerification, Long> {

	OtpVerification findByUserEmail(String userEmail);

	OtpVerification findByUserName(String userName);

}
