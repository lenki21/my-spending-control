package com.mlenkiewicz.model;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;



public class Helper {

	public static LocalDate toLocalDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		LocalDate localeData = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		return localeData;
	}
	public static Date toDate(LocalDate ld) {
		Calendar c = Calendar.getInstance();
		c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());
		Date dateNew = c.getTime();

		return dateNew;
	}
}
