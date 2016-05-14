package osgi_bndtools.calculator.access.web.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import osgi_bndtools.calculator.access.ui.common.CalculatorViewController;
import osgi_bndtools.calculator.access.ui.common.type.EMathOperation;

@ManagedBean(name="simpleCalculatorBean")
@SessionScoped
public class SimpleCalculatorBean extends CalculatorViewController {
	
	// ... business methods
	
	public void setDisplayValue(final String displayValue) {}
	
	public void selectMathOperation(final String mathOperation) {
	
		this.selectMathOperation(EMathOperation.valueOf(mathOperation));
	}
}