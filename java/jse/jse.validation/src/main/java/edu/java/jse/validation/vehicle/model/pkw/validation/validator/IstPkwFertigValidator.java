package edu.java.jse.validation.vehicle.model.pkw.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import edu.java.jse.validation.vehicle.model.pkw.Pkw;
import edu.java.jse.validation.vehicle.model.pkw.validation.annotation.IstPkwFertig;
import edu.java.jse.validation.vehicle.model.pkw.validation.util.PkwValidationUtils;

public class IstPkwFertigValidator implements ConstraintValidator<IstPkwFertig, Pkw> {

	IstPkwFertig constraintAnnotation;

	@Override
	public void initialize(IstPkwFertig constraintAnnotation) {

		this.constraintAnnotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(Pkw pkw, ConstraintValidatorContext context) {

		return PkwValidationUtils.isCompleted(pkw);
	}

}
