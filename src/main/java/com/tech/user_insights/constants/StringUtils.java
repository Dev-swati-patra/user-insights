package com.tech.user_insights.constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StringUtils {
	
	public static boolean isValidObj(final Object inObj) {
        if ((null == inObj)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isEmpty(final String str) {
        if ((str == null) || (str.trim().length() == 0)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean matchPattern(String regex, String data) {
        try {
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(data);
            return matcher.matches();
        } catch (Exception e) {
            log.error("Getting Exception in matchPattern: ", e);
        }
        return false;
    }

    public static boolean isValidMobile(final String inMobile) {
        Pattern pattern = Pattern.compile("[6-9][0-9]{9}");
        Matcher matcher = pattern.matcher(inMobile);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
