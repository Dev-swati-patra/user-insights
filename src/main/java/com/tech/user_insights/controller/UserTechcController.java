package com.tech.user_insights.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserTechcController {

	@PostMapping("/get")
	public String publioc() {
		System.out.println("Entering");
		return "Welcome to user controller.";
	}
	
}
