package eg.com.payment;

import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

	public static YearMonth getValidYearMonthOfCard(String month, String year) {

		DateTimeFormatter ccMonthFormatter = DateTimeFormatter.ofPattern("MM/uu");
		StringBuilder yearMonthBuilder = new StringBuilder().append(month).append("/").append(year);
		try {
			YearMonth validYearMonth = YearMonth.parse(yearMonthBuilder.toString(), ccMonthFormatter);
			return validYearMonth;
		} catch (DateTimeParseException dtpe) {
			System.out.println("Not a valid expiry date: " + yearMonthBuilder.toString());
		}
		return null;
	}

	public static boolean isCardExpirationValid(YearMonth yearMonth) {
		if (YearMonth.now(ZoneId.systemDefault()).isAfter(yearMonth)) {
			return false;
		}
		return true;
	}

}
