package com.apep.util;

public class LogWriter {


	public static String getStackMsg(StackTraceElement[] aStackTraceElements) {
		if (aStackTraceElements == null) {
			return null;
		}
		StringBuilder errorInfo = new StringBuilder();
		
		for (StackTraceElement tStackTraceElement : aStackTraceElements) {
			errorInfo.append(tStackTraceElement.toString()).append("\n");
		}
		return errorInfo.toString();
	}

}
