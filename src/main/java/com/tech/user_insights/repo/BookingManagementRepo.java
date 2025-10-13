package com.tech.user_insights.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.user_insights.pojo.BookingManagement;

@Repository
public interface BookingManagementRepo extends JpaRepository<BookingManagement, Long> {

	List<BookingManagement> findByUserInfo_UserId(Integer userId);

}
