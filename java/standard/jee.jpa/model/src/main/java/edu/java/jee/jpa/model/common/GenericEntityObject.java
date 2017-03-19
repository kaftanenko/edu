package edu.java.jee.jpa.model.common;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import edu.java.jee.jpa.model.common.identity.IIdentifiableByLong;

public abstract class GenericEntityObject implements IIdentifiableByLong, Serializable {

	// ...constants

	private static final long serialVersionUID = 1L;

	// ...

	@Override
	public boolean hasId() {

		return getId() != null;
	}

	// ... helper methods

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
