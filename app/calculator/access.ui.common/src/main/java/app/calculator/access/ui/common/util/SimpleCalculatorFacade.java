package app.calculator.access.ui.common.util;

import java.math.BigDecimal;

import app.calculator.access.ui.common.type.EMathOperation;
import app.calculator.service.api.ISimpleCalculatorService_BigDecimalType;
import app.calculator.service.impl.SimpleCalculatorService_BigDecimalType;

public class SimpleCalculatorFacade {

	// ... dependency properties

	private final ISimpleCalculatorService_BigDecimalType calculatorService = new SimpleCalculatorService_BigDecimalType();

	// ... business methods

	public BigDecimal calculate(final BigDecimal arg, final EMathOperation op) {

		switch (op) {
		case NEGATE:
			return calculatorService.neg(arg);
		default:
			throw new RuntimeException("Unsupported operation: " + op);
		}
	}
	
	public BigDecimal calculate(final BigDecimal arg1, final BigDecimal arg2, final EMathOperation op) {

		switch (op) {
		case ADD:
			return calculatorService.add(arg1, arg2);
		case DIVIDE:
			return calculatorService.div(arg1, arg2);
		case MULTIPLY:
			return calculatorService.mul(arg1, arg2);
		case SUBTRACT:
			return calculatorService.sub(arg1, arg2);
		default:
			throw new RuntimeException("Unsupported operation: " + op);
		}
	}

}
