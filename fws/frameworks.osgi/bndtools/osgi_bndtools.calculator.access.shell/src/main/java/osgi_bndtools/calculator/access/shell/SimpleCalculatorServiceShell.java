package osgi_bndtools.calculator.access.shell;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import osgi_bndtools.calculator.service.api.ISimpleCalculatorService;

@Component(properties = { "osgi.command.scope:String=calc",
		"osgi.command.function:String=add|div|mul|sub" }, provide = Object.class)
public class SimpleCalculatorServiceShell implements ISimpleCalculatorService {

	// ... properties

	private ISimpleCalculatorService calculationService;

	// ... dependency injection methods

	@Reference
	public void setCalculationService(ISimpleCalculatorService calculationService) {

		this.calculationService = calculationService;
	}

	// ... command handling methods

	@Override
	public long add(long arg1, long arg2) {

		return calculationService.add(arg1, arg2);
	}

	@Override
	public double add(double arg1, double arg2) {

		return calculationService.add(arg1, arg2);
	}

	@Override
	public long div(long arg1, long arg2) {

		return calculationService.div(arg1, arg2);
	}

	@Override
	public double div(double arg1, double arg2) {

		return calculationService.div(arg1, arg2);
	}

	@Override
	public long mul(long arg1, long arg2) {

		return calculationService.mul(arg1, arg2);
	}

	@Override
	public double mul(double arg1, double arg2) {

		return calculationService.mul(arg1, arg2);
	}

	@Override
	public long sub(long arg1, long arg2) {

		return calculationService.sub(arg1, arg2);
	}

	@Override
	public double sub(double arg1, double arg2) {

		return calculationService.sub(arg1, arg2);
	}

}
