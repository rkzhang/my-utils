package com.apep.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * 日期格式校验枚举
 * @author rkzhang
 */
public enum DateRegExp {

	yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss",
			"^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|" +
			"(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])" +
			"|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$"),
	
	yyyy_MM_dd("yyyy-MM-dd", 
			"^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|" +
			"(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])" +
			"|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$"),
			
	yyyy_MM("yyyy-MM",
			"^(?!0000)[0-9]{4}-(?:0[1-9]|1[0-2])$");
	

	private SimpleDateFormat dateFormat;
	
	private Pattern regExpPattern;
	
	DateRegExp(String formatStr, String regExp){
		dateFormat = new SimpleDateFormat(formatStr);
		regExpPattern = Pattern.compile(regExp);
	}
	
	public String format(Date date){
		synchronized (dateFormat) {
			Assert.notNull(date);
			return dateFormat.format(date);
		}
	}
	
	public Date parse(String dateStr) throws ParseException{
		synchronized (dateFormat) {
			return dateFormat.parse(dateStr);
		}
	}
	
	public boolean match(String dateStr){
		if(StringUtils.isEmpty(dateStr)){
			return false;
		}
		return regExpPattern.matcher(dateStr.trim()).matches();
	}
	
}
