package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {
	
	public static DateTimeFormatter format= DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	
	public static String dateToString(LocalDate date) {
		String formated = date.format(format);
		return formated;
	}
	
	public static LocalDate stringToDate(String date) {
		LocalDate formated = LocalDate.parse(date, format);
		return formated;
	}

}