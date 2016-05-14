package osgi_bndtools.calculator.service.api;

public interface ISimpleCalculatorService<DATA_TYPE> {

	// ... business methods

	public DATA_TYPE neg(DATA_TYPE arg);
	
	// ...
	
	public DATA_TYPE add(DATA_TYPE arg1, DATA_TYPE arg2);

	public DATA_TYPE div(DATA_TYPE arg1, DATA_TYPE arg2);

	public DATA_TYPE mul(DATA_TYPE arg1, DATA_TYPE arg2);

	public DATA_TYPE sub(DATA_TYPE arg1, DATA_TYPE arg2);

}
