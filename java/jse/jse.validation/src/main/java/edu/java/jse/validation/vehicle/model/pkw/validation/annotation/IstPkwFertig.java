package edu.java.jse.validation.vehicle.model.pkw.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import edu.java.jse.validation.vehicle.model.pkw.validation.validator.IstPkwFertigValidator;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IstPkwFertigValidator.class)
public @interface IstPkwFertig {

	String message() default "{edu.java.jse.validation.vehicle.model.pkw.IstPkwFertig.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
