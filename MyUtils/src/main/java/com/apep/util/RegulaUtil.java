package com.apep.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegulaUtil {
	
	public static boolean matchByString(String value,String matchString){
		Pattern p = Pattern.compile(matchString);
		Matcher m=p.matcher(value);
		return m.matches();
	}
}
