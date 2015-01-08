package com.apep.util.dto;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DateRange {

	private Date beginTime;
	
	private Date endTime;

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
