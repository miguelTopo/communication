package co.edu.udistrital.core.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private DateUtil() {}

	public static Calendar getCurrentCalendar() {
		return Calendar.getInstance();
	}

	public static Date getCurrentDate() {
		return getCurrentCalendar().getTime();
	}

}
