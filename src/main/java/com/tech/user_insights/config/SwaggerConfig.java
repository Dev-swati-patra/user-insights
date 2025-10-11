//package com.tech.user_insights.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//
//@Configuration
//public class SwaggerConfig {
//	
////	@Bean
////	public OpenAPI custOpenAPI() {
////		return new OpenAPI().info(new Info().title("User-Insights").version("1.0").description("Api documentation"));
////	}
//	
//	@Bean
//    public OpenAPI customOpenApi() {
//        final String securitySchemeName = "BearerAuth";
//        return new OpenAPI()
//                .info(new Info()
//                        .title("Student API")
//                        .version("1.0")
//                        .description("APIs for mapping students"))
//                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
//                .components(new Components()
//                        .addSecuritySchemes(securitySchemeName,
//                                new SecurityScheme()
//                                        .name(securitySchemeName)
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));
//    }
//}

package com.tech.user_insights.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenApi() {
		final String securitySchemeName = "BearerAuth";
		return new OpenAPI()
				.info(new Info().title("Tourist API").version("1.0").description("APIs for mapping tourist's "))
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme()
						.name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
