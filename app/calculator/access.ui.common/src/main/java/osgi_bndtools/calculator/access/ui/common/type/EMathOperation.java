package osgi_bndtools.calculator.access.ui.common.type;

public enum EMathOperation {

	// ... constants

	ADD(EMathOperationArity.BINARY),
	DIVIDE(EMathOperationArity.BINARY),
	MULTIPLY(EMathOperationArity.BINARY),
	SUBTRACT(EMathOperationArity.BINARY),
	NEGATE(EMathOperationArity.UNARY);

	// ... properties

	private final EMathOperationArity arity;

	// ... constructors

	private EMathOperation(EMathOperationArity arity) {

		this.arity = arity;
	}

	// ...

	public EMathOperationArity getArity() {

		return arity;
	}

	public boolean isUnary() {

		return arity == EMathOperationArity.UNARY;
	}

	public boolean isBinary() {

		return arity == EMathOperationArity.BINARY;
	}

};
