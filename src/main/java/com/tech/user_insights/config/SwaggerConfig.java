package com.tech.user_insights.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI custOpenAPI() {
		return new OpenAPI().info(new Info().title("welcom api").version("1.0").description("Api documention"));
	}
}
