package com.apep.util.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.apep.util.DateRegExp;

public class DateRegExpTest {

	@Test
	public void testRegExp(){ 
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-01-01 12:21:57"), true);
		
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-1-01 12:21:59"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-01-01 32:21:59"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-01-01  12:21:59"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("12011-01-01 12:21:57"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-01-01 12:21:60"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-01-01 12:69:59"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-02-29 12:59:59"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-02-9 12:59:59"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-02-09 2:59:59"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-02-09 02:59:59"), true);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-02-09 02:9:59"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-02-09 02:07:8"), false);	
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.match("2011-01-01"), false);
		
		Assert.assertEquals(DateRegExp.yyyy_MM_dd.match("2011-01-01"), true);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd.match("2011-02-29"), false);	
		Assert.assertEquals(DateRegExp.yyyy_MM_dd.match("2011-1-01"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd.match("2011-01-1"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd.match("2011-1-1"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM_dd.match("12011-01-01"), false);
		
		Assert.assertEquals(DateRegExp.yyyy_MM.match("2011-12"), true);
		Assert.assertEquals(DateRegExp.yyyy_MM.match("2011-13"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM.match("20119-02"), false);	
		Assert.assertEquals(DateRegExp.yyyy_MM.match("2011-1"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM.match("2011-01ju"), false);
		Assert.assertEquals(DateRegExp.yyyy_MM.match("0000-01"), false);
	}
	
	@Test
	public void testFormat() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Assert.assertNotNull(DateRegExp.yyyy_MM_dd_HH_mm_ss.format(new Date()));
		Date date1 = DateRegExp.yyyy_MM_dd_HH_mm_ss.parse("2011-01-21 12:21:57");
		Assert.assertNotNull(date1);
		
		Assert.assertEquals(date1, dateFormat.parse("2011-01-21 12:21:57"));
		Assert.assertEquals(DateRegExp.yyyy_MM_dd_HH_mm_ss.format(date1), dateFormat.format(date1));
	}
}
