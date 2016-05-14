package app.calculator.access.ui.common;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import app.calculator.access.ui.common.type.EMathOperation;
import app.calculator.access.ui.common.util.BigDecimalUtils;
import app.calculator.access.ui.common.util.SimpleCalculatorFacade;

public class SimpleCalculatorViewController implements ISimpleCalculatorViewController {

	// ... constants

	private static final String MINUS = "-";
	private static final String ZERO = BigDecimal.ZERO.toString();

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

	public SimpleCalculatorViewController() {

		init();
	}

	public void init() {

		resetCurrentArgumentInput();

		selectedOperation = null;
	}

	public SimpleCalculatorFacade getSimpleCalculatorFacade() {

		final SimpleCalculatorFacade simpleCalculatorFacade = new SimpleCalculatorFacade();
		return simpleCalculatorFacade;
	}

	// ... view data access

	@Override
	public String getDisplayValue() {

		return currentArgumentInput;
	}

	@Override
	public String getDecimalSeparator() {

		return decimalSeparator;
	}

	// ... business methods

	@Override
	public void calculate() {

		if (selectedOperation == null) {

			// ... nothing to do: insufficient data for calculation.
		} else {

			final BigDecimal result;

			final BigDecimal arg2 = BigDecimalUtils.stringToBigDecimal(currentArgumentInput, locale);

			if (selectedOperation.isUnary()) {

				result = getSimpleCalculatorFacade().calculate(arg2, selectedOperation);
			} else {
				result = getSimpleCalculatorFacade().calculate(arg1, arg2, selectedOperation);
			}

			selectedOperation = null;

			currentArgumentInput = BigDecimalUtils.bigDecimalToString(result, locale);
			currentArgumentInputFinished = true;
		}
	}

	@Override
	public void cancelInput() {

		resetCurrentArgumentInput();
	}

	@Override
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

	@Override
	public void putInCommaSign() {

		resetCurrentArgumentInput_If_It_Was_Finished();

		if (currentArgumentInput.contains(decimalSeparator)) {

			// ... nothing to do: there is already a comma.
		} else {
			currentArgumentInput += decimalSeparator;
		}
	}

	@Override
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

	@Override
	public void selectMathOperation(final EMathOperation mathOperation) {

		selectedOperation = mathOperation;

		arg1 = BigDecimalUtils.stringToBigDecimal(currentArgumentInput, locale);
		currentArgumentInputFinished = true;

		if (mathOperation.isUnary()) {

			calculate();
		}
	}

}
