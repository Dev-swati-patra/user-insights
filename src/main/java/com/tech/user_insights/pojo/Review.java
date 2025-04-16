package com.tech.user_insights.pojo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "book_id" }) })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo user;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private BookDetails book;

	@Column(nullable = false)
	private Integer rating;

	@Column(columnDefinition = "TEXT")
	private String comment;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

}
