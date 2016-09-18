package edu.java.jse.validation.vehicle.model.parts.reifen.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import edu.java.jse.validation.vehicle.model.parts.reifen.validation.annotation.IstReifenAufgepumpt;

public class IstReifenAufgepumptValidator implements ConstraintValidator<IstReifenAufgepumpt, Double> {

	IstReifenAufgepumpt constraintAnnotation;

	@Override
	public void initialize(IstReifenAufgepumpt constraintAnnotation) {

		this.constraintAnnotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(Double pressureInAtm, ConstraintValidatorContext context) {

		return pressureInAtm > 0;
	}

}
