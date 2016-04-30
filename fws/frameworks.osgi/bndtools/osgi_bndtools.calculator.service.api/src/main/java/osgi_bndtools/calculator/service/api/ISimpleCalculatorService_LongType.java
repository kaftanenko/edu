package osgi_bndtools.calculator.service.api;

public interface ISimpleCalculatorService_LongType  extends ISimpleCalculatorService<Long> {

	// ... business methods

	@Override
	public Long add(Long arg1, Long arg2);

	@Override
	public Long div(Long arg1, Long arg2);

	@Override
	public Long mul(Long arg1, Long arg2);

	@Override
	public Long sub(Long arg1, Long arg2);

}
