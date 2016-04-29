package osgi_bndtools.calculator.access.web.jsf;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class SimpleCalculatorBean implements Serializable {

	// ... constants

	private static final long serialVersionUID = 1L;

	// ... properties

	private long arg1;
	private long arg2;
	private String operation;

	// ... dependencies

//	public ISimpleCalculatorService getSimpleCalculatorService() {
//
//		final ISimpleCalculatorService simpleCalculatorService = new SimpleCalculatorService();
//		return simpleCalculatorService;
//	}

	// ... setter/getter methods

	public long getArg1() {
		return arg1;
	}

	public void setArg1(long arg1) {
		this.arg1 = arg1;
	}

	public long getArg2() {
		return arg2;
	}

	public void setArg2(long arg2) {
		this.arg2 = arg2;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	// ... business methods

	public long getCalculationResult() {

		final long calculationResult;

		if ("+".equals(operation)) {

			calculationResult = arg1 + arg2;
			// calculationResult = getSimpleCalculatorService().add(arg1, arg2);
		} else if ("-".equals(operation)) {

			calculationResult = arg1 - arg2;
			// calculationResult = getSimpleCalculatorService().sub(arg1, arg2);
		} else if ("*".equals(operation)) {

			calculationResult = arg1 * arg2;
			// calculationResult = getSimpleCalculatorService().mul(arg1, arg2);
		} else if ("/".equals(operation)) {

			calculationResult = arg1 / arg2;
			// calculationResult = getSimpleCalculatorService().div(arg1, arg2);
		} else {

			throw new RuntimeException("Unknown operation: " + operation);
		}

		return calculationResult;
	}

}