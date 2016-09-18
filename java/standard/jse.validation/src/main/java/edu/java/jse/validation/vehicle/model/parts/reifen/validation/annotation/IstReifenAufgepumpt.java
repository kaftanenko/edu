package edu.java.jse.validation.vehicle.model.parts.reifen.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import edu.java.jse.validation.vehicle.model.parts.reifen.validator.IstReifenAufgepumptValidator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IstReifenAufgepumptValidator.class)
public @interface IstReifenAufgepumpt {

	String message() default "{edu.java.jse.validation.vehicle.model.parts.reifen.IstReifenAufgepumpt.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
