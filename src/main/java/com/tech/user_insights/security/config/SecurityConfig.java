package com.tech.user_insights.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tech.user_insights.security.filter.JwtAuthenticationEntryPoint;
import com.tech.user_insights.security.filter.JwtFilter;
import com.tech.user_insights.security.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	UserDetailsService userDetailsService() {
		return new UserService();
	}

//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//		return httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
//				.authorizeHttpRequests(
//						auth -> auth.requestMatchers("/auth/**").permitAll().anyRequest().authenticated())
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.authenticationProvider(auththenticationProvider())
//				.exceptionHandling(
//						exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint))
//				.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
//	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/register/V1.0", "/auth/signIn/V1.0", "/auth/forgetPassword/V1.0",
								"/auth/verify_otp/V1.0", "/auth/resetPassword/V1.0", "/swagger-ui/**",
								"/v3/api-docs/**", "/swagger-ui.html")
						.permitAll().requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**")
						.hasAnyRole("USER", "ADMIN") // Allow access to Swagger endpoints
						.anyRequest().authenticated()) // Secure all other endpoints
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(auththenticationProvider())
				.exceptionHandling(
						exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint))
				.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class) // JWT filter
				.build();
	}

	@Bean
	AuthenticationProvider auththenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
