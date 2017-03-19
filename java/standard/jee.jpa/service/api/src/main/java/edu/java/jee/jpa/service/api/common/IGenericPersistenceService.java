package edu.java.jee.jpa.service.api.common;

public interface IGenericPersistenceService<MASTER_ENTITY_TYPE> {

	// ... business methods

	public abstract MASTER_ENTITY_TYPE getById(long id);

	public abstract void saveOrUpdate(MASTER_ENTITY_TYPE entity);

}
