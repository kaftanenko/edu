package app.calculator.access.ui.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BigDecimalUtils {

	// ... business methods

	public static String bigDecimalToString(final BigDecimal number, final Locale locale) {


		return number.toString();
	}

	public static BigDecimal stringToBigDecimal(final String formattedString, final Locale locale) {

		final DecimalFormatSymbols symbols;
		final char groupSeparatorChar;
		final String groupSeparator;
		final char decimalSeparatorChar;
		final String decimalSeparator;
		String fixedString;
		final BigDecimal number;

		symbols = new DecimalFormatSymbols(locale);
		groupSeparatorChar = symbols.getGroupingSeparator();
		decimalSeparatorChar = symbols.getDecimalSeparator();

		if (groupSeparatorChar == '.') {
			groupSeparator = "\\" + groupSeparatorChar;
		} else {
			groupSeparator = Character.toString(groupSeparatorChar);
		}

		if (decimalSeparatorChar == '.') {
			decimalSeparator = "\\" + decimalSeparatorChar;
		} else {
			decimalSeparator = Character.toString(decimalSeparatorChar);
		}

		fixedString = formattedString.replaceAll(groupSeparator, "");
		fixedString = fixedString.replaceAll(decimalSeparator, ".");
		number = new BigDecimal(fixedString);

		return (number);
	}
}
