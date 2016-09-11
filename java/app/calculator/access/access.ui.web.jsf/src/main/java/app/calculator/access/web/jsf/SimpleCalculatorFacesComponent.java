package app.calculator.access.web.jsf;

import java.io.Serializable;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import app.calculator.access.ui.common.ISimpleCalculatorViewController;
import app.calculator.access.ui.common.SimpleCalculatorViewController;
import app.calculator.access.ui.common.type.EMathOperation;

@FacesComponent(value = "simpleCalculatorBean")
public class SimpleCalculatorFacesComponent extends UINamingContainer implements ISimpleCalculatorViewController, Serializable {

	// ... dependency properties

	private ISimpleCalculatorViewController controller = new SimpleCalculatorViewController();

	// ... business methods

	public void setDisplayValue(final String displayValue) {
	}

	public void selectMathOperation(final String mathOperation) {

		this.selectMathOperation(EMathOperation.valueOf(mathOperation));
	}

	// ...
	
	@Override
	public String getDisplayValue() {

		return controller.getDisplayValue();
	}

	@Override
	public String getDecimalSeparator() {

		return controller.getDecimalSeparator();
	}

	@Override
	public void calculate() {

		controller.calculate();
	}

	@Override
	public void cancelInput() {

		controller.cancelInput();
	}

	@Override
	public void deleteLastInputSign() {

		controller.deleteLastInputSign();
	}

	@Override
	public void putInCommaSign() {

		controller.putInCommaSign();
	}

	@Override
	public void putInDigitSign(byte number) {

		controller.putInDigitSign(number);
	}

	@Override
	public void selectMathOperation(EMathOperation mathOperation) {

		controller.selectMathOperation(mathOperation);
	}

}
