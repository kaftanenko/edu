package osgi_bndtools.calculator.access.web.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import osgi_bndtools.calculator.access.ui.common.CalculatorViewController;
import osgi_bndtools.calculator.access.ui.common.type.EMathOperation;

@ManagedBean(name="simpleCalculatorBean")
@SessionScoped
public class SimpleCalculatorBean extends CalculatorViewController {
	
	// ... constants

	public final static String PARAM_NAME_NUMBER_BUTTON = "num";
	public final static String PARAM_NAME_COMMA_BUTTON = "comma";
	public final static String PARAM_NAME_OPERATION_BUTTON = "op";

	public final static String PARAM_VALUE_OPERATION_ADD = EMathOperation.ADD.name();
	public final static String PARAM_VALUE_OPERATION_DIV = EMathOperation.DIVIDE.name();
	public final static String PARAM_VALUE_OPERATION_MUL = EMathOperation.MULTIPLY.name();
	public final static String PARAM_VALUE_OPERATION_NEG = EMathOperation.NEGATE.name();
	public final static String PARAM_VALUE_OPERATION_SUB = EMathOperation.SUBTRACT.name();

	public final static String PARAM_VALUE_OPERATION_CANCEL = "cancel";
	public final static String PARAM_VALUE_OPERATION_DELETE = "delete";

	public final static String PARAM_VALUE_OPERATION_CALCULATE = "calculate";
	
	// ... business methods
	
	public void setDisplayValue(final String displayValue) {}
	
	public void selectMathOperation(final String mathOperation) {
	
		this.selectMathOperation(EMathOperation.valueOf(mathOperation));
	}
}