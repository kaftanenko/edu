<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="style/calculator.css" />
<title>Simple Calculator (JSP)</title>
</head>
<body>

	<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>

	<%@ page import="java.util.Locale"%>
	<%@ page import="java.util.Set"%>

	<%@ page import="java.math.BigDecimal"%>

	<%@ page import="java.text.DecimalFormatSymbols"%>

	<%@ page import="app.calculator.access.ui.common.*"%>
	<%@ page import="app.calculator.access.ui.common.type.*"%>

	<%!;
	private final static String PARAM_NAME_NUMBER_BUTTON = "num";
	private final static String PARAM_NAME_COMMA_BUTTON = "comma";
	private final static String PARAM_NAME_OPERATION_BUTTON = "op";

	private final static String PARAM_VALUE_OPERATION_ADD = EMathOperation.ADD.name();
	private final static String PARAM_VALUE_OPERATION_DIV = EMathOperation.DIVIDE.name();
	private final static String PARAM_VALUE_OPERATION_MUL = EMathOperation.MULTIPLY.name();
	private final static String PARAM_VALUE_OPERATION_NEG = EMathOperation.NEGATE.name();
	private final static String PARAM_VALUE_OPERATION_SUB = EMathOperation.SUBTRACT.name();

	private final static String PARAM_VALUE_OPERATION_CANCEL = "cancel";
	private final static String PARAM_VALUE_OPERATION_DELETE = "delete";

	private final static String PARAM_VALUE_OPERATION_CALCULATE = "calculate";

	// ...

	private ISimpleCalculatorViewController calculatorViewController = new SimpleCalculatorViewController();%>

	<%
		final Set<String> parameterNames = request.getParameterMap().keySet();

		if (parameterNames.contains(PARAM_NAME_OPERATION_BUTTON)) {

			// ... OPERATIONAL BUTTON CLICKED

			final String opParamValue = request.getParameter(PARAM_NAME_OPERATION_BUTTON);

			if (opParamValue.equals(PARAM_VALUE_OPERATION_CALCULATE)) {

				// ... OPERATIONAL BUTTON CLICKED: CALCULATE

				calculatorViewController.calculate();

			} else if (opParamValue.equals(PARAM_VALUE_OPERATION_CANCEL)) {

				// ... OPERATIONAL BUTTON CLICKED: CANCEL

				calculatorViewController.cancelInput();

			} else if (opParamValue.equals(PARAM_VALUE_OPERATION_DELETE)) {

				// ... OPERATIONAL BUTTON CLICKED: CANCEL

				calculatorViewController.deleteLastInputSign();

			} else {

				// ... OPERATIONAL BUTTON CLICKED: MATH OPERATION

				final EMathOperation mathOperation = EMathOperation.valueOf(opParamValue);
				calculatorViewController.selectMathOperation(mathOperation);
			}
		} else {

			// ... INPUT BUTTON CLICKED

			if (parameterNames.contains(PARAM_NAME_NUMBER_BUTTON)) {

				// ... INPUT BUTTON CLICKED: NUMBER

				final String numParamValue = request.getParameter(PARAM_NAME_NUMBER_BUTTON);
				final byte numberValue = Byte.valueOf(numParamValue);

				calculatorViewController.putInDigitSign(numberValue);

			} else if (parameterNames.contains(PARAM_NAME_COMMA_BUTTON)) {

				// ... INPUT BUTTON CLICKED: COMMA

				calculatorViewController.putInCommaSign();
			}
		}
	%>

	<form action="" method="post">


		<table class="calculator-grid">
			<colgroup>
				<col class="numbers-column" span="3" />
				<col class="math-op-column" />
				<col class="last-op-column" />
			</colgroup>
			<tr>
				<th colspan="5"><input type="text" class="display-line"
					value="<%=calculatorViewController.getDisplayValue()%>" /></th>
			</tr>
			<tr class="key-button-row">
				<td><button class="key-button" type="submit" name="num"
						value="7">7</button></td>
				<td><button class="key-button" type="submit" name="num"
						value="8">8</button></td>
				<td><button class="key-button" type="submit" name="num"
						value="9">9</button></td>
				<td><button class="key-button" type="submit" name="op"
						value="<%=PARAM_VALUE_OPERATION_DIV%>">/</button></td>
				<td><button class="key-button" type="submit" name="op"
						value="<%=PARAM_VALUE_OPERATION_CANCEL%>">C</button></td>
			</tr>
			<tr class="key-button-row">
				<td><button class="key-button" class="key-button" type="submit"
						name="num" value="4">4</button></td>
				<td><button class="key-button" type="submit" name="num"
						value="5">5</button></td>
				<td><button class="key-button" type="submit" name="num"
						value="6">6</button></td>
				<td><button class="key-button" type="submit" name="op"
						value="<%=PARAM_VALUE_OPERATION_MUL%>">*</button></td>
				<td><button class="key-button" type="submit" name="op"
						value="<%=PARAM_VALUE_OPERATION_DELETE%>">&#x21D0;</button></td>
			</tr>
			<tr class="key-button-row">
				<td><button class="key-button" type="submit" name="num"
						value="1">1</button></td>
				<td><button class="key-button" type="submit" name="num"
						value="2">2</button></td>
				<td><button class="key-button" type="submit" name="num"
						value="3">3</button></td>
				<td><button class="key-button" type="submit" name="op"
						value="<%=PARAM_VALUE_OPERATION_SUB%>">-</button></td>
				<td rowspan="2"><button class="key-button" type="submit"
						name="op" value="<%=PARAM_VALUE_OPERATION_CALCULATE%>">=</button></td>
			</tr>
			<tr class="key-button-row">
				<td><button class="key-button" type="submit" name="num"
						value="0">0</button></td>
				<td><button class="key-button" type="submit" name="comma"><%=calculatorViewController.getDecimalSeparator()%></button></td>
				<td><button class="key-button" type="submit" name="op"
						value="<%=PARAM_VALUE_OPERATION_NEG%>">&plusmn;</button></td>
				<td><button class="key-button" type="submit" name="op"
						value="<%=PARAM_VALUE_OPERATION_ADD%>">+</button></td>
			</tr>
		</table>

	</form>
</body>
</html>