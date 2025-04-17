package com.tech.user_insights.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDto {
	private String title;
	private String author;
	private String price;
	private String stock;
	private String category;
	private String description;
	private String imageUrl;
}
