package edu.java.jse.validation.vehicle.model.common.type;

import javax.validation.ConstraintViolation;

public class ConstraintViolationTemplate {

	// ... properties

	private final Class<?> beanClass;

	private final String propertyPath;

	private final String messageTemplate;

	// ... constructors

	public ConstraintViolationTemplate(final ConstraintViolation<?> constraintViolation) {

		this.beanClass = constraintViolation.getRootBeanClass();
		this.propertyPath = constraintViolation.getPropertyPath().toString();
		this.messageTemplate = constraintViolation.getMessageTemplate();
	}

	public ConstraintViolationTemplate(final Class<?> beanClass, final String propertyPath, final String messageTemplate) {

		this.beanClass = beanClass;
		this.propertyPath = propertyPath;
		this.messageTemplate = messageTemplate;
	}

	// ... getter/setter methods

	public Class<?> getBeanClass() {

		return beanClass;
	}

	public String getPropertyPath() {

		return propertyPath;
	}

	public String getMessageTemplate() {

		return messageTemplate;
	}

	// ... helper methods

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((beanClass == null) ? 0 : beanClass.hashCode());
		result = prime * result + ((messageTemplate == null) ? 0 : messageTemplate.hashCode());
		result = prime * result + ((propertyPath == null) ? 0 : propertyPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstraintViolationTemplate other = (ConstraintViolationTemplate) obj;
		if (beanClass == null) {
			if (other.beanClass != null)
				return false;
		} else if (!beanClass.equals(other.beanClass))
			return false;
		if (messageTemplate == null) {
			if (other.messageTemplate != null)
				return false;
		} else if (!messageTemplate.equals(other.messageTemplate))
			return false;
		if (propertyPath == null) {
			if (other.propertyPath != null)
				return false;
		} else if (!propertyPath.equals(other.propertyPath))
			return false;
		return true;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("ConstraintViolationTemplate [beanClass=");
		builder.append(beanClass);
		builder.append(", propertyPath=");
		builder.append(propertyPath);
		builder.append(", messageTemplate=");
		builder.append(messageTemplate);
		builder.append("]");
		return builder.toString();
	}

}
