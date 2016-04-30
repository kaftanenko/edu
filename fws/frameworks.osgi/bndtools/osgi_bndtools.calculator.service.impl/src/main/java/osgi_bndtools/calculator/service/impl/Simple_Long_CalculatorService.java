package osgi_bndtools.calculator.service.impl;

import org.osgi.service.component.annotations.Component;

import osgi_bndtools.calculator.service.api.ISimpleCalculatorService;

@Component
public class SimpleCalculatorService implements ISimpleCalculatorService {

	// ...

	@Override
	public long add(long arg1, long arg2) {

		return arg1 + arg2;
	}

	@Override
	public long div(long arg1, long arg2) {

		return arg1 / arg2;
	}

	@Override
	public long mul(long arg1, long arg2) {

		return arg1 * arg2;
	}

	@Override
	public long sub(long arg1, long arg2) {

		return arg1 - arg2;
	}

	// ...

	@Override
	public double add(double arg1, double arg2) {

		return arg1 + arg2;
	}

	@Override
	public double div(double arg1, double arg2) {

		return arg1 / arg2;
	}

	@Override
	public double mul(double arg1, double arg2) {

		return arg1 * arg2;
	}

	@Override
	public double sub(double arg1, double arg2) {

		return arg1 - arg2;
	}

}
