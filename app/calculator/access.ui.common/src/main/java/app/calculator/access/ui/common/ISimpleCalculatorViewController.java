package app.calculator.access.ui.common;

import app.calculator.access.ui.common.type.EMathOperation;

public interface ISimpleCalculatorViewController {

	String getDisplayValue();

	String getDecimalSeparator();

	void calculate();

	void cancelInput();

	void deleteLastInputSign();

	void putInCommaSign();

	void putInDigitSign(byte number);

	void selectMathOperation(EMathOperation mathOperation);

}
