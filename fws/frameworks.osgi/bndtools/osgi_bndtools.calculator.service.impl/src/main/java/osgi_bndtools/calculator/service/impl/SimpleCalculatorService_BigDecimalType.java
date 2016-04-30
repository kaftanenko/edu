package osgi_bndtools.calculator.service.impl;

import java.math.BigDecimal;

import org.osgi.service.component.annotations.Component;

import osgi_bndtools.calculator.service.api.ISimpleCalculatorService_BigDecimalType;

@Component
public class SimpleCalculatorService_BigDecimalType implements ISimpleCalculatorService_BigDecimalType {

	// ... business methods

	@Override
	public BigDecimal add(BigDecimal arg1, BigDecimal arg2) {

		return arg1.add(arg2);
	}

	@Override
	public BigDecimal div(BigDecimal arg1, BigDecimal arg2) {

		return arg1.divide(arg2);
	}

	@Override
	public BigDecimal mul(BigDecimal arg1, BigDecimal arg2) {

		return arg1.multiply(arg2);
	}

	@Override
	public BigDecimal sub(BigDecimal arg1, BigDecimal arg2) {

		return arg1.subtract(arg2);
	}

}
