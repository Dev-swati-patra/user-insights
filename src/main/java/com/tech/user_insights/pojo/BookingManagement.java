package com.tech.user_insights.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tech.user_insights.constants.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "core", name = "booking_management")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingManagement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private Long bookingId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo userInfo;

	@ManyToOne
	@JoinColumn(name = "spot_id")
	private SpotDetails spotDetails;

	@Column(name = "booking_date")
	private LocalDateTime bookingDate;

	@Column(name = "visit_date")
	private LocalDateTime visitDate;

	@Column(name = "number_of_people")
	private Integer numberOfPeople;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status")
	private BookingStatus paymentStatus;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "remarks")
	private String remarks;

	@Enumerated(EnumType.STRING)
	@Column(name = "booking_status")
	private BookingStatus bookingStatus;

}
