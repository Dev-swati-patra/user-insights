package com.tech.user_insights.constants;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ClientInfoDetails {
	
	public String getClientOs(String user) {
		if (user.toLowerCase().contains("android")) {
			return "Android";
		} else if (user.toLowerCase().contains("iphone") || user.toLowerCase().contains("ipad")) {
			return "Ios";
		} else {
			return "Unknown";
		}
	}

	public static String getClientIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-Forwarded-For");
		if (null == ipAddress || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (null == ipAddress || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (null == ipAddress || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

}
