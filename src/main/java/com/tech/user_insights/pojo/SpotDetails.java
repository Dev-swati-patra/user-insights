package com.tech.user_insights.pojo;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

import com.tech.user_insights.constants.StatusMessage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "core", name = "spot_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spot_id")
	private Long spotId;

	@Column(name = "spot_name")
	private String spotName;

	@Column(name = "spot_city")
	private String spotCity;

	@Column(name = "spot_district")
	private String spotDistrict;

	@Column(name = "spot_state")
	private String spotState;

	@Column(name = "spot_country")
	private String spotCountry;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "price_per_person")
	private BigDecimal pricePerPerson;

	@Column(name = "opening_time")
	private Time openingTime;

	@Column(name = "closing_time")
	private Time closingTime;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "email")
	private String email;

	@Column(columnDefinition = "JSON")
	private String images; // store image URLs as JSON array

	@Column(name = "average_rating", precision = 2, scale = 1)
	private BigDecimal averageRating;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusMessage status;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "spotDetails", cascade = CascadeType.ALL)
	private List<BookingManagement> bookings;

}
