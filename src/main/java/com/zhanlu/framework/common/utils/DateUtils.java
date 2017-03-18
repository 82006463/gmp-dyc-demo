package com.zhanlu.framework.common.utils;

import org.joda.time.DateTime;

/**
 * 
 * @author yuqs
 * @since 0.1
 */
public class DateUtils {
	private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

	public static String getCurrentDay() {
		return new DateTime().toString(DATE_FORMAT_YMD);
	}

}
