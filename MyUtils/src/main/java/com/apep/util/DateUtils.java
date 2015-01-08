package com.apep.util;

import java.util.Calendar;
import java.util.Date;

import com.apep.util.dto.DateRange;

public class DateUtils extends org.apache.commons.lang.time.DateUtils{

	public static DateRange getOneDateRange(Date date){
		DateRange dateRange = new DateRange();
		
		Calendar beginTime = Calendar.getInstance();
		beginTime.setTime(date);
		beginTime.set(Calendar.HOUR_OF_DAY, 0);
		beginTime.set(Calendar.MINUTE, 0);
		beginTime.set(Calendar.SECOND, 0);
		beginTime.set(Calendar.MILLISECOND, 0);
		dateRange.setBeginTime(beginTime.getTime());
		
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(date);
		endTime.set(Calendar.DATE, endTime.get(Calendar.DATE) + 1);
		endTime.set(Calendar.HOUR_OF_DAY, 0);
		endTime.set(Calendar.MINUTE, 0);
		endTime.set(Calendar.SECOND, 0);
		endTime.set(Calendar.MILLISECOND, 0);
		dateRange.setEndTime(endTime.getTime());
		
		return dateRange;
	}
	
	public static Date getNextDay(Date date){
		
		Calendar firstDayCalendar = Calendar.getInstance();
		firstDayCalendar.setTime(date);
		
		firstDayCalendar.set(Calendar.DATE, firstDayCalendar.get(Calendar.DATE) + 1);
		firstDayCalendar.set(Calendar.HOUR_OF_DAY, 0);
		firstDayCalendar.set(Calendar.MINUTE, 0);
		firstDayCalendar.set(Calendar.SECOND, 0);
		firstDayCalendar.set(Calendar.MILLISECOND, 0);
		return firstDayCalendar.getTime();
	}
}
