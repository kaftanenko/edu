package osgi_bndtools.calculator.access.ui.common;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import osgi_bndtools.calculator.access.ui.common.type.EMathOperation;
import osgi_bndtools.calculator.access.ui.common.util.BigDecimalUtils;

public class CalculatorViewController {

	// ... constants

	private static final String MINUS = "-";
	private static final String ZERO = BigDecimal.ZERO.toString();

	// ... dependency properties

	private SimpleCalculatorFacade calculator = new SimpleCalculatorFacade();

	// ... configuration properties

	final Locale locale = Locale.US;

	final DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
	final String decimalSeparator = "" + symbols.getDecimalSeparator();
	final String groupSeparator = "" + symbols.getGroupingSeparator();

	// ... controller state properties

	BigDecimal arg1;
	String currentArgumentInput;
	boolean currentArgumentInputFinished;

	EMathOperation selectedOperation;

	// ... constructor

	public CalculatorViewController() {

		init();
	}

	public void init() {

		resetCurrentArgumentInput();

		selectedOperation = null;
	}

	// ... view data access

	public String getDisplayValue() {

		return currentArgumentInput;
	}

	public String getDecimalSeparator() {

		return decimalSeparator;
	}

	// ... business methods

	public void calculate() {

		if (selectedOperation == null) {

			// ... nothing to do: insufficient data for calculation.
		} else {

			final BigDecimal result;

			final BigDecimal arg2 = BigDecimalUtils.stringToBigDecimal(currentArgumentInput, locale);

			if (selectedOperation.isUnary()) {

				result = calculator.calculate(arg2, selectedOperation);
			} else {
				result = calculator.calculate(arg1, arg2, selectedOperation);
			}

			selectedOperation = null;

			currentArgumentInput = BigDecimalUtils.bigDecimalToString(result, locale);
			currentArgumentInputFinished = true;
		}
	}

	public void cancelInput() {

		resetCurrentArgumentInput();
	}

	public void deleteLastInputSign() {

		resetCurrentArgumentInput_If_It_Was_Finished();

		final int currentArgumentLength = currentArgumentInput.length();

		if (currentArgumentLength == 1) {

			currentArgumentInput = ZERO;
		}
		if (currentArgumentLength == 2 && currentArgumentInput.startsWith(MINUS)) {

			currentArgumentInput = ZERO;
		}
		if (currentArgumentLength > 1) {

			currentArgumentInput = currentArgumentInput.substring(0, currentArgumentLength - 1);
		}
	}

	public void putInCommaSign() {

		resetCurrentArgumentInput_If_It_Was_Finished();

		if (currentArgumentInput.contains(decimalSeparator)) {

			// ... nothing to do: there is already a comma.
		} else {
			currentArgumentInput += decimalSeparator;
		}
	}

	public void putInDigitSign(final byte number) {

		resetCurrentArgumentInput_If_It_Was_Finished();

		if (currentArgumentInput.equals(ZERO)) {

			currentArgumentInput = "" + number;
		} else {
			currentArgumentInput += number;
		}
	}

	private void resetCurrentArgumentInput() {

		currentArgumentInput = ZERO;
		currentArgumentInputFinished = false;
	}

	private void resetCurrentArgumentInput_If_It_Was_Finished() {

		if (currentArgumentInputFinished) {

			resetCurrentArgumentInput();
		}
	}

	public void selectMathOperation(EMathOperation mathOperation) {

		selectedOperation = mathOperation;

		arg1 = BigDecimalUtils.stringToBigDecimal(currentArgumentInput, locale);
		currentArgumentInputFinished = true;

		if (mathOperation.isUnary()) {

			calculate();
		}
	}

}
