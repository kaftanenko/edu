package app.calculator.service.impl;

import aQute.bnd.annotation.component.Component;
import app.calculator.service.api.ISimpleCalculatorService_LongType;

@Component
public class SimpleCalculatorService_LongType implements ISimpleCalculatorService_LongType {

	// ... business methods

	@Override
	public Long neg(Long arg) {

		return - arg;
	}

	// ...
	
	@Override
	public Long add(Long arg1, Long arg2) {

		return arg1 + arg2;
	}

	@Override
	public Long div(Long arg1, Long arg2) {

		return arg1 / arg2;
	}

	@Override
	public Long mul(Long arg1, Long arg2) {

		return arg1 * arg2;
	}

	@Override
	public Long sub(Long arg1, Long arg2) {

		return arg1 - arg2;
	}

}
