package com.tech.user_insights.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserTechcController {

	@GetMapping("/get")
	public String publioc() {
		System.out.println("Entering");
		return "Welcome to my Insight Family";
	}
}
