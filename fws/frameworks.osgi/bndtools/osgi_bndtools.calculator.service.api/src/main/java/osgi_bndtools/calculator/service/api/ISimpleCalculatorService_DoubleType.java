package osgi_bndtools.calculator.service.api;

public interface ISimpleCalculatorService_DoubleType  extends ISimpleCalculatorService<Double> {

	// ... business methods

	@Override
	public Double add(Double arg1, Double arg2);

	@Override
	public Double div(Double arg1, Double arg2);

	@Override
	public Double mul(Double arg1, Double arg2);

	@Override
	public Double sub(Double arg1, Double arg2);

}
