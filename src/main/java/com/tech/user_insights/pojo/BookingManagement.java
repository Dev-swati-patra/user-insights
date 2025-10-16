package com.tech.user_insights.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tech.user_insights.constants.StringUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "users", name = "booking_management")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingManagement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private Long bookingId;

	@Column(name = "booking_ref_id")
	private String bookingRefId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo userInfo;

	@ManyToOne
	@JoinColumn(name = "spot_id")
	private SpotDetails spotDetails;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy hh:mm a", timezone = "Asia/Kolkata")
	@Column(name = "booking_date")
	private Timestamp bookingDate;

	@Column(name = "visit_date")
	private Timestamp visitDate;

	@Column(name = "number_of_people")
	private Integer numberOfPeople;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "payment_status")
	private String paymentStatus;

	@Column(name = "created_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy hh:mm a", timezone = "Asia/Kolkata")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "booking_status")
	private String bookingStatus;

	@PrePersist
	protected void onCreate() {
		this.createdAt = StringUtils.getCurrentTimeStamp();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = StringUtils.getCurrentTimeStamp();
	}

}
