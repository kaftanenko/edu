package app.calculator.service.impl;

import aQute.bnd.annotation.component.Component;
import app.calculator.service.api.ISimpleCalculatorService_DoubleType;

@Component
public class SimpleCalculatorService_DoubleType implements ISimpleCalculatorService_DoubleType {

	// ... business methods

	@Override
	public Double neg(Double arg) {

		return -arg;
	}

	// ...

	@Override
	public Double add(Double arg1, Double arg2) {

		return arg1 + arg2;
	}

	@Override
	public Double div(Double arg1, Double arg2) {

		return arg1 / arg2;
	}

	@Override
	public Double mul(Double arg1, Double arg2) {

		return arg1 * arg2;
	}

	@Override
	public Double sub(Double arg1, Double arg2) {

		return arg1 - arg2;
	}

}
