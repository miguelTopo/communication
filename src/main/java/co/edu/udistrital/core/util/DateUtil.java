package co.edu.udistrital.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private final static String HH24_MM = "HH:mm";

	private DateUtil() {}

	public static Calendar getCurrentCalendar() {
		return Calendar.getInstance();
	}

	public static Date getCurrentDate() {
		return getCurrentCalendar().getTime();
	}

	public static String getTime(Calendar calendar) {
		if (calendar == null)
			return "";
		return formatCalendar(calendar, HH24_MM);
	}

	public static String formatCalendar(Calendar calendar, String pattern) {
		if (calendar == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(calendar.getTime());
	}

	public static String getShortDayName(Calendar calendar) {
		if (calendar == null)
			return CoreConst.STRING_EMPTY;
		SimpleDateFormat pattern = new SimpleDateFormat("EEE");
		return pattern.format(calendar.getTime());
	}
	
	public static String getShortMonthName(Calendar calendar) {
		if (calendar == null)
			return CoreConst.STRING_EMPTY;
		SimpleDateFormat pattern = new SimpleDateFormat("MMM");
		return pattern.format(calendar.getTime());
	}

	public static String datedDate(Calendar calendar) {
		if (calendar == null)
			return CoreConst.STRING_EMPTY;
		Calendar today = getCurrentCalendar();
		if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)
			&& calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))
			return "Hoy: " + getTime(calendar);
		return getShortDayName(calendar) + " " + calendar.get(Calendar.DAY_OF_MONTH) + " " + getShortMonthName(calendar) +" " + getTime(calendar);
	}
	

}
