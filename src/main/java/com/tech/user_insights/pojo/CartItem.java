package com.tech.user_insights.pojo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
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
@Table(name = "cart_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cart_user"))
	private UserInfo user;

	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cart_book"))
	private BookDetails book;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "added_at", updatable = false, insertable = false)
	private LocalDateTime addedAt;
}
