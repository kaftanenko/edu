package osgi_bndtools.calculator.service.api;

import java.math.BigDecimal;

public interface ISimpleCalculatorService_BigDecimalType extends ISimpleCalculatorService<BigDecimal> {

	// ... business methods

	@Override
	public BigDecimal add(BigDecimal arg1, BigDecimal arg2);

	@Override
	public BigDecimal div(BigDecimal arg1, BigDecimal arg2);

	@Override
	public BigDecimal mul(BigDecimal arg1, BigDecimal arg2);

	@Override
	public BigDecimal sub(BigDecimal arg1, BigDecimal arg2);

}
