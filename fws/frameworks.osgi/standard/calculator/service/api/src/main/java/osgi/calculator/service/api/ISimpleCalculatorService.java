package osgi.calculator.service.api;

public interface ISimpleCalculatorService {

	// ... business methods

	// ... arguments of a long type

	public long add(long arg1, long arg2);

	public long div(long arg1, long arg2);

	public long mul(long arg1, long arg2);

	public long sub(long arg1, long arg2);

	// ... arguments of a double type

	public double add(double arg1, double arg2);

	public double div(double arg1, double arg2);

	public double mul(double arg1, double arg2);

	public double sub(double arg1, double arg2);

}
