package com.tech.user_insights.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminTechController {
	
	@PostMapping("/addBooks/V1.0")
	public String addBooks_V1_0() {
		System.out.println("Entering");
		return "Welcome to admin controller.";
	}

}
