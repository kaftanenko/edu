package app.calculator.access.shell;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import app.calculator.service.api.ISimpleCalculatorService_LongType;

@Component(properties = { "osgi.command.scope:String=calc",
		"osgi.command.function:String=neg|add|div|mul|sub" }, provide = Object.class)
public class SimpleCalculatorServiceShell implements ISimpleCalculatorService_LongType {

	// ... properties

	private ISimpleCalculatorService_LongType calculationService;

	// ... dependency injection methods

	@Reference
	public void setCalculationService(ISimpleCalculatorService_LongType calculationService) {

		this.calculationService = calculationService;
	}

	// ... command handling methods

	@Override
	public Long neg(Long arg) {

		return calculationService.neg(arg);
	}
	
	// ...

	@Override
	public Long add(Long arg1, Long arg2) {

		return calculationService.add(arg1, arg2);
	}

	@Override
	public Long div(Long arg1, Long arg2) {

		return calculationService.div(arg1, arg2);
	}

	@Override
	public Long mul(Long arg1, Long arg2) {

		return calculationService.mul(arg1, arg2);
	}

	@Override
	public Long sub(Long arg1, Long arg2) {

		return calculationService.sub(arg1, arg2);
	}

}
