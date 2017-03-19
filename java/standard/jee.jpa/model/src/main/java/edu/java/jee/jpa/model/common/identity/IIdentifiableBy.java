package edu.java.jee.jpa.model.common.identity;

import java.io.Serializable;

public interface IIdentifiableBy<ID extends Serializable> {

	public ID getId();

	public boolean hasId();

}
